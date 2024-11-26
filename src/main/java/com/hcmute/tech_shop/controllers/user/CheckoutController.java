package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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
        // Extract voucher names and values
        List<Voucher> vouchers = voucherService.findByQuantityGreaterThan(0);
        List<String> voucherNames = vouchers.stream().map(Voucher::getName).toList();
        List<BigDecimal> voucherValues = vouchers.stream().map(Voucher::getValue).toList();

        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("voucherNames", voucherNames);
        model.addAttribute("voucherValues", voucherValues);

        return "user/checkout";
    }

    @PostMapping("/process")
    @ResponseBody
    public String process(Model model, HttpSession session,
                          @RequestParam("paymentMethod") String paymentMethod,
                          @RequestParam("addressId") Long addressId,
                          @RequestParam("voucher") String voucherCode,
                          @RequestParam("newPrice") BigDecimal newPrice) {
        // Get the current user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        // Check if the voucher code is null
        Voucher voucher = new Voucher();
        if (!Objects.equals(voucherCode, "")) {
            // Find the voucher by its name
            voucher = voucherService.findByName(voucherCode).get();
        }
        // Find the address by its ID
        Address address = addressService.findById(addressId).get();
        // Find the payment method by its name
        Payment payment = paymentService.findByName(paymentMethod);
        // Retrieve the cart detail list from the session
        List<CartDetailResponse> cartDetailList = (List<CartDetailResponse>) session.getAttribute("cartDetailList");
        // Retrieve the cart from the session
        Cart cart = (Cart) session.getAttribute("cart");
        // Update the cart's total price
        cart.setTotalPrice(newPrice);
        session.setAttribute("cart", cart);

        if (!Objects.equals(voucherCode, "")) {
            orderService.createOrder(user, newPrice, voucher, payment, address, cart.getId(), cartDetailList);
        } else {
            orderService.createOrder(user, newPrice, payment, address, cart.getId(), cartDetailList);
        }

        String redirectUrl;
        if (payment.equals("paypal")) {
            redirectUrl = "/user/checkout/paypal";
        } else if (payment.equals("vnpay")) {
            redirectUrl = "/user/checkout/vnpay";
        } else {
            // Handle other payment methods or default case
            redirectUrl = "/user/home";
        }

        // Return the redirect URL as a JSON string
        return "{\"redirectUrl\": \"" + redirectUrl + "\"}";
    }
}
