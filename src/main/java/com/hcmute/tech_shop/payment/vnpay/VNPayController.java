package com.hcmute.tech_shop.payment.vnpay;

import com.hcmute.tech_shop.entities.Cart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping()
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @GetMapping({"/user/checkout/vnpay"})
    public String submidOrder(HttpServletRequest request, HttpSession session) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        Cart cart = (Cart) session.getAttribute("cart");
        String vnpayUrl = vnPayService.createOrder(request, cart.getTotalPrice().intValue(), baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

//        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

//        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if(paymentStatus == 1){
            return "redirect:/user/home";
        }
        return "redirect:/user/checkout";

//        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}

