package com.hcmute.tech_shop.controllers.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/user/{user_id}/orders/{order_id}/{product_id}/")
public class OrderDetailController {
    @Autowired
    IOrderDetailService iOrderDetailService;

    @GetMapping("")
    public String index(Model model,
                        @PathVariable("order_id") Long orderId,
                        @PathVariable("product_id") Long productId,
                        @RequestParam("page") int page,
                        @RequestParam("limit") int limit) {
        OrderDetail orderDetail = iOrderDetailService.]findById(orderId, productId);
        model.addAttribute("order", orderDetail);
        return "user/orders/detail/";
    }

    // localhost:8080/user/{user_id}/orders/{order_id}/edit
    @GetMapping("/edit")
    public ModelAndView edit(ModelMap model,
                                @Valid @PathVariable("user_id") Long userId,
                             @Valid @PathVariable("order_id") Long orderId,
                             @Valid @PathVariable("product_id") Long productId) {
        Optional<orderDetail> orderDetail = iOrderDetailService.findById(orderId, productId);
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        if (orderDetail.isPresent()) { // if order is found
            OrderDetail entity = orderDetail.get();
            // Copy properties from entity to categoryDTO
            BeanUtils.copyProperties(entity, orderDetailDTO);
            orderDTO.setIsEdit(true);
            // Add orderDetailDTO to model attribute to pass data from controller to view
            model.addAttribute("orderDetail", orderDetailDTO);
            // Return view with model attribute
            return new ModelAndView("admin/orders/orderDetail/add", model);
        }
        model.addAttribute("message", "Không tìm thấy danh mục");
        // localhost:8080/user/{user_id}/orders/{order_id}/{product_id}
        return new ModelAndView("redirect:/user/" + userId + "/orders/" + orderId + "/" + productId, model);
    }

    @GetMapping("/delete/{order_id}")
    public String show(Model model,
                       @Valid @PathVariable("user_id") Long userId,
                       @Valid @PathVariable("order_id") Long orderId,
                       @Valid @PathVariable("product_id") Long productId) {
        Optional<Order> order = iOrderService.findById(orderId);
        if (order.isPresent()) {
            // Soft delete order

            model.addAttribute("order", order.get());
            return "user/orders/show";
        }
        return "redirect:/user/" + userId + "/orders";
    }
}
