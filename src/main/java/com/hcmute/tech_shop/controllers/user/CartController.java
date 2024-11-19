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
    public String cart(Model model ) {
        UserRequest userRequest = getUser();
        List<CartDetail> cartDetailList = new ArrayList<>();
        List<CartDetail> cartDetailListFull = new ArrayList<>();
        if(userRequest != null) {
            Cart cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = new Cart();
                cart.setUserId(userRequest.getId());
                cart.setTotalPrice(BigDecimal.ZERO);
                cart = cartService.createCart(cart);
            }
            cartDetailList = cartDetailServiceImpl.findAllByCart_Id(cart.getId());
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }

            model.addAttribute("cart",cart);
        }
        model.addAttribute("cartDetailListFull", cartDetailListFull);
        model.addAttribute("cartDetailList", cartDetailList);
        return "user/cart";
    }

    @PostMapping("/cart-add")
    public String addToCart(Model model, @Valid @RequestParam("productId") Long productId,@RequestParam("quantity") int quantity) {
        UserRequest userRequest = getUser();

        if(userRequest != null) {
            Cart cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = new Cart();
                cart.setUserId(userRequest.getId());
                cart.setTotalPrice(BigDecimal.ZERO);
                cart = cartService.createCart(cart);
            }
            Product product = productServiceImpl.findById(productId).orElse(null);
            CartDetailRequest cartDetailRequest = new CartDetailRequest();
            CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
            if(cartDetail != null) {
                cartDetailRequest.setCart(cart);
                cartDetailRequest.setProduct(product);
                cartDetailRequest.setQuantity(quantity+cartDetail.getQuantity());
                cartDetailRequest.setTotalPrice(product.getPrice().multiply( new BigDecimal(cartDetailRequest.getQuantity())));
                if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                    String error = "Could not update cart detail";
                    model.addAttribute("error", error);
                }
            }
            else {
                cartDetailRequest.setCart(cart);
                cartDetailRequest.setProduct(product);
                cartDetailRequest.setQuantity(quantity);
                cartDetailRequest.setTotalPrice(product.getPrice().multiply(new BigDecimal(quantity)));
                if (!cartDetailServiceImpl.create(cartDetailRequest)) {
                    String error = "Could not create cart detail";
                    model.addAttribute("error", error);
                }
            }
            model.addAttribute("cart",cart);
        }
        return "redirect:/user/home";
    }

    @PostMapping("/inc-cart")
    public String incrementCart(Model model, @Valid @RequestParam("productId") Long productId) {
        UserRequest userRequest = getUser();

        if(userRequest != null) {
            Cart cart = cartService.findByCustomerId(userRequest.getId());
            Product product = productServiceImpl.findById(productId).orElse(null);
            CartDetailRequest cartDetailRequest = new CartDetailRequest();
            CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
            cartDetailRequest.setCart(cart);
            cartDetailRequest.setQuantity(cartDetail.getQuantity()+1);
            cartDetailRequest.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartDetailRequest.getQuantity())));
            cartDetailRequest.setProduct(product);
            if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not create cart detail";
                model.addAttribute("error", error);
            }
            model.addAttribute("cart",cart);
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/dec-cart")
    public String decrementCart(Model model, @Valid @RequestParam("productId") Long productId) {
        UserRequest userRequest = getUser();

        if(userRequest != null) {
            Cart cart = cartService.findByCustomerId(userRequest.getId());
            Product product = productServiceImpl.findById(productId).orElse(null);
            CartDetailRequest cartDetailRequest = new CartDetailRequest();
            CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
            cartDetailRequest.setCart(cart);
            cartDetailRequest.setProduct(product);
            if (cartDetail.getQuantity() > 1){
                cartDetailRequest.setQuantity(cartDetail.getQuantity()-1);
                cartDetailRequest.setTotalPrice(product.getPrice().multiply(new BigDecimal(cartDetailRequest.getQuantity())));
                if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                    String error = "Could not create cart detail";
                    model.addAttribute("error", error);
                }
            }
            model.addAttribute("cart",cart);
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/delete-all")
    public String deleteAllCart(Model model) {
        UserRequest userRequest = getUser();
        if(userRequest != null) {
            Cart cart = cartService.findByCustomerId(userRequest.getId());
            if(cart != null) {
                if(!cartDetailServiceImpl.deleteAll(cart.getId())){
                    String error = "Could not delete cart detail";
                    model.addAttribute("error", error);
                }

            }
        }
        List<CartDetail> cartDetailList = new ArrayList<>();
        model.addAttribute("cartDetailList", cartDetailList);

        return "user/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @Valid @PathVariable("id") Long productId) {
        UserRequest userRequest = getUser();
        if(userRequest != null) {
            Cart cart = cartService.findByCustomerId(userRequest.getId());
            CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
            if(cartDetail != null) {
                if(!cartDetailServiceImpl.delete(cartDetail)) {
                    String error = "Could not delete cart detail";
                    model.addAttribute("error", error);
                }

            }
            model.addAttribute("cart",cart);
        }
        List<CartDetail> cartDetailList = new ArrayList<>();
        model.addAttribute("cartDetailList", cartDetailList);

        return "redirect:/user/cart";
    }
}
