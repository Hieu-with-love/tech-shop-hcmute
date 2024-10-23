package com.hcmute.tech_shop.controllers.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/{user_id}/orders")
public class OrderController {
    @Autowired
    IOrderService iOrderService;

    // Get all orders of user
    @GetMapping("")
    public String index(Model model,
                        @Valid @PathVariable("user_id") Long userId,
                        @RequestParam("page") int page,
                        @RequestParam("limit") int limit) {
        List<Order> orders = iOrderService.findByUserId(userId);
        model.addAttribute("orders", orders);
        return "user/orders/index";
    }

    // localhost:8080/user/{user_id}/orders/{order_id}
    // Get order information and list of order details
    @GetMapping("/{order_id}")
    public ModelAndView getOrderInfo(@PathVariable("user_id") Long userId,
                                     @PathVariable("order_id") Long orderId) {
        ModelAndView modelAndView = new ModelAndView("user/orders/info");
        Order order = iOrderService.findById(orderId).get();
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @GetMapping("/add")
    public String add(Model model, @Valid @PathVariable("user_id") Long userId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIsEdit(false);
        // Add orderDTO to model attribute with key "order" to pass data from controller to view
        model.addAttribute("order", orderDTO);
        return "admin/orders/add";
    }

    @GetMapping("/edit/{order_id}")
    public ModelAndView edit(ModelMap model,
                             @Valid @PathVariable("user_id") Long userId,
                             @Valid @PathVariable("order_id") Long orderId) {
        Optional<order> order = iOrderService.findById(orderId);
        OrderDTO orderDTO = new OrderDTO();
        if (order.isPresent()) { // if order is found
            Order entity = order.get();
            // Copy properties from entity to categoryDTO
            BeanUtils.copyProperties(entity, orderDTO);
            orderDTO.setIsEdit(true);
            // Add categoryDTO to model attribute with key "order" to pass data from controller to view
            model.addAttribute("order", orderDTO);
            // Return view with model attribute
            return new ModelAndView("admin/orders/add", model);
        }
        model.addAttribute("message", "Không tìm thấy danh mục");
        return new ModelAndView("redirect:/user/" + userId + "/orders", model);
    }

    @GetMapping("/delete/{order_id}")
    public String show(Model model,
                       @Valid @PathVariable("user_id") Long userId,
                       @Valid @PathVariable("order_id") Long orderId) {
        Optional<Order> order = iOrderService.findById(orderId);
        if (order.isPresent()) {
            // Soft delete order

            model.addAttribute("order", order.get());
            return "user/orders/show";
        }
        return "redirect:/user/" + userId + "/orders";
    }


    @PostMapping("/insert")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @PathVariable("user_id") Long userId,
                                     @Valid @ModelAttribute("category") OrderDTO orderDTO,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/orders/add");
        }

        Order order = new Order();
        // Copy properties from orderDTO to order
        BeanUtils.copyProperties(orderDTO, order);
        // Save category to database
        iOrderService.save(order);

        if (orderDTO.getIsEdit() == true) {
            model.addAttribute("message", "Order updated successfully");
        } else {
            model.addAttribute("message", "Order is save");
        }

        return new ModelAndView("redirect:/admin/orders", model);
    }
}
