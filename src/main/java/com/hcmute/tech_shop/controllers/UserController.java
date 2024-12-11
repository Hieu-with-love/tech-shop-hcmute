package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.Impl.EmailServiceImpl;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    EmailServiceImpl emailService;
    private final ICartDetailService cartDetailService;
    private final CartService cartService;

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

    @GetMapping("/cart")
    public String showCart() {
        return "user/cart";
    }

    @GetMapping("/login")
    public String login() {
        return "user/sign-in";
    }

    @GetMapping("/logout")
    public String logout(){

        return "redirect:/user/home";
    }

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        UserRequest userRequest = new UserRequest();
        model.addAttribute("userRegister", userRequest);
        return "user/sign-up";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @Valid @ModelAttribute("userRegister") UserRequest userRequest,
                           BindingResult result
                           ) throws IOException {
        // Catch null value exception
        if (result.hasErrors()) {
            return "user/sign-up";
        }

        boolean is = userService.createUser(userRequest, result);

        // Catch logic by system exception
        if (result.hasErrors()) {
            return "user/sign-up";
        }

        return "redirect:/login?success";
    }

    @PostMapping("/user/updateProfileImage")
    public String updateProfileImage(@RequestParam("image") MultipartFile image, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            model.addAttribute("error", "User not found");
            return "user/my-account";
        }

        try {
            userService.updateProfileImage(user, image);
            model.addAttribute("message", "Profile image updated successfully");
        } catch (IOException e) {
            model.addAttribute("error", "Failed to update profile image");
        }

        return "redirect:/user/my-account";
    }


    @GetMapping("/verify-account")
    public String verifyAccount(Model model, @RequestParam("token") String token) {
        boolean isSuccess = userService.verifyToken(token);
        if (!isSuccess) {
            model.addAttribute("error", "Invalid token");
            return "user/sign-up";
        }else {
            model.addAttribute("message", "Verify successfully");
            return "redirect:/login";
        }
    }

    @GetMapping("/forgot-password")
    public String getPageForgotPassword(Model model) {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(Model model,
                                 @Valid @RequestParam("email") String email,
                                 BindingResult result) {
        if (email == null) {
            model.addAttribute("emailError", "Email bị null, moi nhập");
            return "user/forgot-password";
        }
        try{
            emailService.sendEmailToReactivePassword(email);
        }catch (Exception ex){
            model.addAttribute("emailError", "Email " + email + "không tồn tại!");
            return "user/forgot-password";
        }
        return "redirect:/login?success";
    }

    @GetMapping("/user/about-us")
    public String aboutUs(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CartDetailResponse> cartDetailList = new ArrayList<>();

        int numberProductInCart = 0;

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            User user = userService.getUserByUsername(username);

            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = cartService.createCart(new Cart(null, BigDecimal.ZERO,userRequest.getId(),null));
            }
            cartDetailList = cartDetailService.getAllItems(cartDetailService.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
            model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));
        }

        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);
        return "user/about-us";
    }

}
