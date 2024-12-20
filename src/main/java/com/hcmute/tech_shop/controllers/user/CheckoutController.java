package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.entities.Payment;
import com.hcmute.tech_shop.services.Impl.CurrencyService;
import com.hcmute.tech_shop.services.interfaces.*;
import com.hcmute.tech_shop.utils.Constant;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.hcmute.tech_shop.utils.PriceUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/checkout")
public class CheckoutController {
    private final IOrderService orderService;
    private final UserService userService;
    private final IAddressService addressService;
    private final IVoucherService voucherService;
    private final IPaymentService paymentService;
    private final APIContext apiContext;
    private final CartService cartService;
    private final ICartDetailService cartDetailService;
    private final CurrencyService currencyService;

    @GetMapping("")
    public String checkout(Model model, HttpSession session, @RequestParam("selectedProducts")List<Long> selectedProducts) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<Address> addresses = addressService.findByUser_Username(username);
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.findById(cart.getId());
        List<CartDetailResponse> cartDetailList = cartDetailService.getAllItems(cartDetailService.findAllByCart_Id(cart.getId()));
        List<CartDetailResponse> cartDetailResponseToBuy = new ArrayList<>();
        int numberProductInCart = cartDetailList.size();
        if(cartDetailList.size() > 3) {
            cartDetailList = cartDetailList.subList(0, 3);
        }
        model.addAttribute("isEmptyCart", cartDetailList.isEmpty());
        session.setAttribute("user", user);
        model.addAttribute("cart",cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);
        model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));


        BigDecimal totalPriceToPayment = BigDecimal.ZERO;

        for (Long productId : selectedProducts) {
            CartDetailResponse cartDetailResponse = cartDetailService.convertToCartDetailReponse(cartDetailService.findByCart_IdAndProductId(cart.getId(), productId));
            totalPriceToPayment = totalPriceToPayment.add(cartDetailResponse.getTotalPrice());
            cartDetailResponseToBuy.add(cartDetailResponse);
        }

        // Extract voucher names and values
        List<Voucher> vouchers = voucherService.findValidVoucher();
        List<String> voucherNames = vouchers.stream().map(Voucher::getName).toList();
        List<BigDecimal> voucherValues = vouchers.stream().map(Voucher::getValue).toList();

        boolean hasAddress = !addresses.isEmpty();

        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("cartDetailResponseToBuy", cartDetailResponseToBuy);
        model.addAttribute("voucherNames", voucherNames);
        model.addAttribute("voucherValues", voucherValues);
        model.addAttribute("hasAddress", hasAddress);
        model.addAttribute("totalPriceToPayment", totalPriceToPayment);
        model.addAttribute("totalPriceOfCart", cartService.getCartResponse(cart));

        session.setAttribute("cartDetailListToBuy",cartDetailResponseToBuy);

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
        // Find the address by its ID
        Address address = addressService.findById(addressId).get();
        // Find the payment method by its name
        Payment payment = paymentService.findByName(paymentMethod);
        // Retrieve the cart detail list from the session
        // Retrieve the cart from the session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart = cartService.findById(cart.getId());
            // Update the cart's total price
            // Set the updated cart back into the session
            session.setAttribute("totalPriceToPayment", newPrice);
        }

        List<CartDetailResponse> cartDetailList = (List<CartDetailResponse>) session.getAttribute("cartDetailListToBuy");

        if (!Objects.equals(voucherCode, "")) {
            // Check if the voucher code is null
            Voucher voucher = voucherService.findValidVoucher(voucherCode);
            orderService.createOrder(user, newPrice, voucher, payment, address, cart.getId(), cartDetailList);
        } else {
            orderService.createOrder(user, newPrice, payment, address, cart.getId(), cartDetailList);
        }

        String redirectUrl;
        if (payment.getName().equals("paypal")) {
            return "{\"redirectUrl\": \"/user/checkout/paypal\"}";
        } else if (payment.getName().equals("vnpay")) {
            return "{\"redirectUrl\": \"/user/checkout/vnpay\"}";
        } else {
            // Handle other payment methods or default case
            redirectUrl = "/user/home";
        }

        // Return the redirect URL as a JSON string
        return "{\"redirectUrl\": \"" + redirectUrl + "\"}";
    }

    @GetMapping("/paypal")
    public String paypalCheckout(HttpSession session, HttpServletResponse response) {
        try {
            // Lấy thông tin giỏ hàng từ session
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                throw new IllegalStateException("Cart is empty");
            }

            // Tổng số tiền trong giỏ hàng (VND)
            BigDecimal totalPriceVND = (BigDecimal) session.getAttribute("totalPriceToPayment");

            BigDecimal exchangeRate = currencyService.getExchangeRateVNDToUSD();
            BigDecimal amountInUSD = totalPriceVND.multiply(exchangeRate);


            // Tạo giao dịch PayPal với số tiền USD
            String approvalUrl = createPayPalPayment(amountInUSD);

            // Chuyển hướng người dùng đến PayPal để thanh toán
            response.sendRedirect(approvalUrl);
            return null; // Không cần trả về giao diện vì đã chuyển hướng
        } catch (Exception e) {
            e.printStackTrace();
            orderService.deleteFailOrder();
            return "redirect:/user/checkout"; // Quay lại trang checkout nếu có lỗi
        }
    }

    public String createPayPalPayment(BigDecimal amount) throws PayPalRESTException {
        // Tạo đối tượng số tiền
        com.paypal.api.payments.Amount paypalAmount = new com.paypal.api.payments.Amount();
        paypalAmount.setCurrency("USD");
        paypalAmount.setTotal(String.format("%.2f", amount)); // Định dạng số tiền

        // Tạo đối tượng giao dịch
        com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
        transaction.setAmount(paypalAmount);
        transaction.setDescription("Order payment for user checkout");

        List<com.paypal.api.payments.Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Tạo đối tượng người thanh toán
        com.paypal.api.payments.Payer payer = new com.paypal.api.payments.Payer();
        payer.setPaymentMethod("paypal");

        // Tạo đối tượng thanh toán
        com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
        payment.setIntent("sale"); // Loại giao dịch: "sale"
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Đặt URL chuyển hướng khi thành công hoặc hủy
        com.paypal.api.payments.RedirectUrls redirectUrls = new com.paypal.api.payments.RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/user/checkout/paypal/cancel"); // URL khi hủy thanh toán
        redirectUrls.setReturnUrl("http://localhost:8080/user/checkout/paypal/success"); // URL khi thanh toán thành công
        payment.setRedirectUrls(redirectUrls);

        // Tạo giao dịch qua PayPal
        com.paypal.api.payments.Payment createdPayment = payment.create(apiContext);

        // Lấy URL phê duyệt từ danh sách liên kết
        for (com.paypal.api.payments.Links link : createdPayment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return link.getHref(); // Trả về URL để chuyển hướng người dùng
            }
        }

        // Nếu không tìm thấy URL phê duyệt
        throw new PayPalRESTException("Approval URL not found");
    }

    /**
     * Xử lý khi thanh toán thành công
     */
    @GetMapping("/paypal/success")
    public String paypalSuccess(@RequestParam("paymentId") String paymentId,
                                @RequestParam("PayerID") String payerId,
                                HttpSession session) {
        try {
            // Lấy thông tin thanh toán từ PayPal
            com.paypal.api.payments.Payment payment = com.paypal.api.payments.Payment.get(apiContext, paymentId);

            // Thực hiện giao dịch
            com.paypal.api.payments.PaymentExecution paymentExecution = new com.paypal.api.payments.PaymentExecution();
            paymentExecution.setPayerId(payerId);
            com.paypal.api.payments.Payment executedPayment = payment.execute(apiContext, paymentExecution);

            // Cập nhật trạng thái đơn hàng hoặc thực hiện logic sau khi thanh toán thành công
            // Thm thuộc tính
            session.setAttribute("paypalPaymentId", paymentId);

            // Chuyển hướng đến trang thành công
//            return "redirect:/user/checkout/success";
            return "redirect:/user/home";
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "redirect:/user/checkout"; // Quay lại trang checkout nếu có lỗi
        }
    }

    /**
     * Xử lý khi thanh toán bị hủy
     */
    @GetMapping("/paypal/cancel")
    public String paypalCancel() {
        return "redirect:/user/checkout"; // Quay lại trang checkout
    }

    @PostMapping("/save-address")
    public ResponseEntity<?> saveAddress(@Valid @RequestBody Map<String, String> params,
                                         BindingResult result){
        try{
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            userService.saveAddress(params);
            return ResponseEntity.ok("Success");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Error");
        }

    }

}
