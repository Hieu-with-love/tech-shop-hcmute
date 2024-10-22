package com.hcmute.tech_shop.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/{user_id}/orders")
public class OrderController {
    @Autowired
    IOrderService iOrderService;

    @GetMapping("")
    public String index(Model model, @Valid @PathVariable("user_id") Long id, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        List<Order> orders = iOrderService.findAll();
        model.addAttribute("orders", orders);
        return "user/orders/index";
    }

    @GetMapping("/delete/{order_id}")
    public String show(Model model, @Valid @PathVariable("user_id") Long userId, @Valid @PathVariable("order_id") Long orderId) {
        Optional<Order> order = iOrderService.findById(orderId);
        if (order.isPresent()) {
            // Soft delete order

            model.addAttribute("order", order.get());
            return "user/orders/show";
        }
        return "redirect:/user/" + userId + "/orders";
    }
}
