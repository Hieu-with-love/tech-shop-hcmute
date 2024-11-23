package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/checkout")
public class CheckoutController {
    private final ICartDetailService cartDetailService;
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    private final UserService userService;
    private final IAddressService addressService;
    private final IVoucherService voucherService;
    private final IPaymentService paymentService;

    @GetMapping("")
    public String checkout(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<Address> addresses = addressService.findByUser_Username(username);
        Cart cart = (Cart) session.getAttribute("cart");
        List<CartDetailResponse> cartDetailList = (List<CartDetailResponse>) session.getAttribute("cartDetailList");

        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        return "user/checkout";
    }

    @GetMapping("/process")
    public String process(Model model, HttpSession session,
                          @RequestParam("paymentMethod") String paymentMethod,
                          @RequestParam("addressId") Long addressId,
                          @RequestParam("voucher") String voucherCode) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        Voucher voucher = voucherService.findByName(voucherCode).get();
        Address address = addressService.findById(addressId).get();
        Payment payment = paymentService.findByName(paymentMethod);
        List<CartDetailResponse> cartDetailList = (List<CartDetailResponse>) session.getAttribute("cartDetailList");

        if (payment.equals("paypal")) {
            return "redirect:/user/checkout/paypal";
        } else if (payment.equals("vnpay")) {
            return "redirect:/user/checkout/vnpay";
        }

        if (orderService.createOrder(user, BigDecimal.ZERO, voucher, payment, cartDetailList)) {
            return "redirect:/user/home";
        }
        return "redirect:/user/checkout";
    }
}
