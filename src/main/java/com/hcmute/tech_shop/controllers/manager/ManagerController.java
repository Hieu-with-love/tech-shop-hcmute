package com.hcmute.tech_shop.controllers.manager;

import com.hcmute.tech_shop.entities.User;
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

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final UserService userService;
    private final IOrderService orderService;
    private final IVoucherService voucherService;
    private final IProductService productService;

    @GetMapping("/home")
    public String dashboard(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        session.setAttribute("user", user);
        model.addAttribute("totalPurchase", orderService.getTotalPurchaseDueForDeliveredOrders());
        model.addAttribute("totalOrders", orderService.getTotalOrder());
        model.addAttribute("totalProductSold", orderService.getTotalProductsSold());
        model.addAttribute("totalAvailableVouchers", voucherService.getAvailableVoucherCount());
        model.addAttribute("totalPendingOrders", orderService.getTotalOrderForShipping());
        model.addAttribute("totalDeliveredOrders", orderService.getTotalOrderDelivered());
        model.addAttribute("totalCanceledOrders", orderService.getTotalOrderCancelled());
        model.addAttribute("totalShippingOrders", orderService.getTotalOrderShipping());
        model.addAttribute("recentlyOrders", orderService.getRecentlyOrders());
        model.addAttribute("recentlyProducts", productService.getRecentlyProducts());
        model.addAttribute("topSellerProducts", productService.getTop4BestSellingProducts());
        return "manager/index";
    }
}
