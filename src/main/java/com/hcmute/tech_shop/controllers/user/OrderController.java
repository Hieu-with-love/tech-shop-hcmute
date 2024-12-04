package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.enums.OrderStatus;
import com.hcmute.tech_shop.services.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Long id) {
        Order order = orderService.findById(id).get();
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("order", order);
        return "user/order-detail";
    }

    @PostMapping("/cancelled")
    public String cancel(Model model, @RequestParam Long id) throws IOException {
        orderService.orderCancelled(id);
        return "redirect:/user/home";
    }
}
