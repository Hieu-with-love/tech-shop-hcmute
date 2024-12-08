package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.ProfileDto;
import com.hcmute.tech_shop.dtos.responses.LoyalCustomerRes;
import com.hcmute.tech_shop.entities.*;
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
import java.util.List;

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

        List<Product> top4Products = productService.getTop4BestSellingProducts();
        model.addAttribute("top4Products", top4Products);

        List<Product> top4NewProducts = productService.get4NewProducts();
        model.addAttribute("top4NewProducts", top4NewProducts);

        List<LoyalCustomerRes> topLoyalCustomers = userService.getTop4LoyalCustomers();
        model.addAttribute("loyalCustomers", topLoyalCustomers);

        return "admin/index";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
            com.hcmute.tech_shop.entities.User user = userService.getUserByUsername(username);
            ProfileDto profileDto = ProfileDto.builder()
                    .username(username)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phone(user.getPhoneNumber())
                    .gender(user.getGender())
                    .dob(user.getDateOfBirth())
                    .status(user.isActive())
                    .image(user.getImage())
                    .build();
            model.addAttribute("profileDto", profileDto);
        return "admin/users/profile";
    }
}
