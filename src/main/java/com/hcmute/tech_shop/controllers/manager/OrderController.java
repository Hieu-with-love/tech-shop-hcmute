package com.hcmute.tech_shop.controllers.manager;

import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.enums.OrderStatus;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller("controllerOfManager")
@RequestMapping("/manager/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final IPaymentService paymentService;
    private final IVoucherService voucherService;
    private final UserService userService;

    @GetMapping("") // localhost:8080/manager/orders
    public String index(Model model) {
        List<Payment> payments = paymentService.findAll();
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("orders", orderService.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .toList());
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("payments", payments);
        model.addAttribute("vouchers", vouchers);
        return "manager/orders/orderlist";
    }

    @GetMapping("/detail")
    public String orderDetail(Model model, @RequestParam Long id) {
        Optional<Order> order = orderService.findById(id);
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("order", order);
        model.addAttribute("shippers", userService.findByRoleName("shipper"));
        return "manager/orders/orderDetail";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Order order = orderService.findById(id).get();
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("order", order);
        return "manager/orders/editOrder";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable(value = "id") Long id) {
        Map<String, String> response = new HashMap<>();
        if (orderService.deleteById(id)) {
            response.put("status", "success");
            response.put("message", "Order deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to delete order.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/pending")
    public String pending(@RequestParam Long id) throws IOException {
        orderService.orderPending(id);
        return "redirect:/manager/orders";
    }

    @PostMapping("/shipping")
    public String shipping(@RequestParam Long id,  @RequestParam Long shipper) throws IOException {
        orderService.orderShipping(id, shipper);
        return "redirect:/manager/orders";
    }

    @PostMapping("/cancelled")
    public String cancel(@RequestParam Long id) throws IOException {
        orderService.orderCancelled(id);
        return "redirect:/manager/orders";
    }
}
