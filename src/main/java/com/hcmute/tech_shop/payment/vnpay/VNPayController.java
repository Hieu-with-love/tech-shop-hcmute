package com.hcmute.tech_shop.payment.vnpay;

import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.services.Impl.OrderServiceImpl;
import com.hcmute.tech_shop.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequestMapping()
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private OrderServiceImpl orderService;

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
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // Định dạng đầu vào
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Định dạng đầu ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Chuyển chuỗi sang LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(paymentTime, inputFormatter);

        // Chuyển LocalDateTime sang chuỗi định dạng mới
        String formattedDate = dateTime.format(outputFormatter);

        String totalPriceVND = totalPrice.substring(0, totalPrice.length() - 2);

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", Constant.formatter.format(Long.parseLong(totalPriceVND)));
        model.addAttribute("paymentTime", formattedDate);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 0){
            orderService.deleteFailOrder();
        }

        return paymentStatus == 1 ? "user/orderSuccess" : "user/orderFail";

    }
}

