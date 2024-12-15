package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.entities.Wishlist;
import com.hcmute.tech_shop.enums.OrderStatus;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.services.interfaces.IOrderService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final ICartDetailService cartDetailService;

    public UserRequest getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dob(user.getDateOfBirth())
                .active(user.isActive())
                .image(user.getImage())
                .build();
        return userRequest;
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = new Cart();

        List<CartDetailResponse> cartDetailListFull = new ArrayList<>();
        int numberProductInCart = 0;

        if(!username.equals("anonymousUser")) {
            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            cartDetailListFull = cartDetailService.getAllItems(cartDetailService.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailListFull.size();
            if(cartDetailListFull.size() > 3) {
                cartDetailListFull = cartDetailListFull.subList(0, 3);
            }
            model.addAttribute("isEmptyCart", cartDetailListFull.isEmpty());
        }

        Order order = orderService.findById(id).get();

        User user = userService.getUserByUsername(username);
        model.addAttribute("cart",cart);
        model.addAttribute("cartDetailList", cartDetailListFull);
        model.addAttribute("numberProductInCart", numberProductInCart);
        model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));

        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("order", order);
        model.addAttribute("paymentMethod", order.getPayment().getName());
        return "user/order-detail";
    }

    @PostMapping("/cancelled")
    public String cancel(Model model, @RequestParam Long id) throws IOException {
        orderService.orderCancelled(id);
        return "redirect:/user/my-account";
    }

    @PostMapping("/completed")
    public String completedOrder(Model model, @RequestParam Long id) {
        orderService.orderCompleted(id);
        model.addAttribute("msg", "Đã hoàn thành đơn hàng");
        return "redirect:/user/my-account";
    }

    @PostMapping("/refund")
    public String refundOrder(Model model, @RequestParam Long id) {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        orderService.orderRefund(id);
        orderService.orderCompleted(id);
        model.addAttribute("msg", "Đã hoàn tiền cho đơn hàng");
        model.addAttribute("balanceVND", user.getBalance());
        return "user/wallet";
    }
}
