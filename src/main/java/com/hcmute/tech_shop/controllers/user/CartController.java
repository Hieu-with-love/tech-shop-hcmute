package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.CartDetailRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.CartDetail;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ICartDetailService cartDetailServiceImpl;

    @Autowired
    private IProductService productServiceImpl;

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

    @GetMapping("")
    public String cart(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CartDetail> cartDetailList = new ArrayList<>();
        List<CartDetail> cartDetailListFull = new ArrayList<>();

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = cartService.createCart(new Cart(null,BigDecimal.ZERO,userRequest.getId(),null));
            }
            cartDetailList = cartDetailServiceImpl.findAllByCart_Id(cart.getId());
            cartDetailListFull = cartDetailList;
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
        }

        User user = userService.getUserByUsername(username);
        session.setAttribute("user", user);
        model.addAttribute("cart",cart);
        model.addAttribute("cartDetailListFull", cartDetailListFull);
        model.addAttribute("cartDetailList", cartDetailList);
        return "user/cart";
    }

    @PostMapping("/cart-add")
    public String addToCart(Model model, @Valid @RequestParam("productId") Long productId,@RequestParam("quantity") int quantity) {
        Cart cart;
        UserRequest userRequest = getUser();
        cart = cartService.findByCustomerId(userRequest.getId());
        if(cart == null) {
            cart = cartService.createCart(new Cart(null,BigDecimal.ZERO,userRequest.getId(),null));
        }

        Product product = productServiceImpl.findById(productId).orElse(null);
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
        CartDetailRequest cartDetailRequest = new CartDetailRequest();
        BigDecimal price;

        if(cartDetail != null) {
            price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()+quantity));
            cartDetailRequest = new CartDetailRequest(quantity + cartDetail.getQuantity(), price, cart, product);
            if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not update cart detail";
                model.addAttribute("error", error);
            }
        }
        else
        {
            price = product.getPrice().multiply(new BigDecimal(quantity));
            cartDetailRequest = new CartDetailRequest(quantity,price, cart, product);
                if (!cartDetailServiceImpl.create(cartDetailRequest)) {
                    String error = "Could not create cart detail";
                    model.addAttribute("error", error);
                }
        }
        return "redirect:/user/home";
    }

    @PostMapping("/inc-cart")
    public String incrementCart(Model model, @Valid @RequestParam("productId") Long productId) {
        UserRequest userRequest = getUser();
        Cart cart = cartService.findByCustomerId(userRequest.getId());

        Product product = productServiceImpl.findById(productId).orElse(null);
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
        CartDetailRequest cartDetailRequest = new CartDetailRequest();
        BigDecimal price;

        if(cartDetail != null) {
            price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()+1));
            cartDetailRequest = new CartDetailRequest(cartDetail.getQuantity() + 1, price, cart, product);
            if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not update cart detail";
                model.addAttribute("error", error);
            }
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/dec-cart")
    public String decrementCart(Model model, @Valid @RequestParam("productId") Long productId) {
        UserRequest userRequest = getUser();
        Cart cart = cartService.findByCustomerId(userRequest.getId());

        Product product = productServiceImpl.findById(productId).orElse(null);
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
        CartDetailRequest cartDetailRequest = new CartDetailRequest();
        BigDecimal price;

        if(cartDetail != null) {
            price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()-1));
            cartDetailRequest = new CartDetailRequest(cartDetail.getQuantity() - 1, price, cart, product);
            if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not update cart detail";
                model.addAttribute("error", error);
            }
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/delete-all")
    public String deleteAllCart(Model model) {
        UserRequest userRequest = getUser();
        Cart cart = cartService.findByCustomerId(userRequest.getId());
        if(cart != null) {
            if(!cartDetailServiceImpl.deleteAll(cart.getId())){
                String error = "Could not delete cart detail";
                model.addAttribute("error", error);
            }
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @Valid @PathVariable("id") Long productId) {
        UserRequest userRequest = getUser();

        Cart cart = cartService.findByCustomerId(userRequest.getId());
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
        if(cartDetail != null) {
            if(!cartDetailServiceImpl.delete(cartDetail)) {
                String error = "Could not delete cart detail";
                model.addAttribute("error", error);
            }
        }

        return "redirect:/user/cart";
    }
}
