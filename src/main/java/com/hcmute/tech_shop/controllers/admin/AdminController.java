package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.entities.Voucher;
import com.hcmute.tech_shop.services.interfaces.IOrderService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final IOrderService orderService;
    private final IVoucherService voucherService;
    private final IProductService productService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        session.setAttribute("user", user);

        BigDecimal totalPurchaseDue = orderService.getTotalPurchaseDueForDeliveredOrders();
        model.addAttribute("totalPurchaseDue", totalPurchaseDue);

        int totalAvailableVouchers = voucherService.getAvailableVoucherCount();
        model.addAttribute("totalAvailableVouchers", totalAvailableVouchers);

        int totalProductsSold = orderService.getTotalProductsSold();
        model.addAttribute("totalProductsSold", totalProductsSold);

        int totalProducts = productService.getTotalStockQuantity();
        model.addAttribute("totalProducts", totalProducts);

        int userCount = userService.getCountUsersByRoleUser();
        model.addAttribute("userCount", userCount);

        int shipperCount = userService.getCountUsersByRoleShipper();
        model.addAttribute("shipperCount", shipperCount);

        int totalOrders = orderService.getTotalOrder();
        model.addAttribute("totalOrders", totalOrders);

        int totalOrdersPending = orderService.getTotalOrderForShipping();
        model.addAttribute("totalOrdersPending", totalOrdersPending);


        return "admin/index";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        return "admin/users/profile";
    }
}
