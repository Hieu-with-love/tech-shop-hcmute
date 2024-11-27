package com.hcmute.tech_shop.controllers.manager;

import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.enums.OrderStatus;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller("controllerOfManager")
@RequestMapping("/manager/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final IPaymentService paymentService;
    private final IVoucherService voucherService;

    @GetMapping("") // localhost:8080/manager/orders
    public String index(Model model) {
        List<Order> orders = orderService.findAll();
        List<Payment> payments = paymentService.findAll();
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("payments", payments);
        model.addAttribute("vouchers", vouchers);
        return "manager/orders/orderlist";
    }

    @GetMapping("/detail")
    public String orderDetail(Model model, @RequestParam Long id) {
        Optional<Order> order = orderService.findById(id);
        model.addAttribute("order", order);
        return "manager/orders/orderDetail";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Order order = orderService.findById(id).get();
        model.addAttribute("order", order);
        return "manager/orders/editOrder";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        orderService.deleteById(id);
        return "redirect:/manager/orders";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @RequestParam Long id) throws IOException {

        return "redirect:/manager/orders";
    }
}
