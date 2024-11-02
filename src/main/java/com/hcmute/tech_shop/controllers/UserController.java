package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.Impl.EmailServiceImpl;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    EmailServiceImpl emailService;

    @GetMapping("/login")
    public String login() {
        return "user/sign-in";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        UserRequest userRequest = new UserRequest();
        model.addAttribute("userRegister", userRequest);
        return "user/sign-up";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @ModelAttribute("registerUser") UserRequest userRequest,
                           BindingResult result
                           ){
        // Kiem tra username da ton tai chua?
        if (userService.existsUsername(userRequest.getUsername())) {
            result.addError(new FieldError("userRegister", "username",
                    "Username da ton tai. Vui long nhap username khac"));
        }
        if (userService.existsEmail(userRequest.getEmail())) {
            result.addError(new FieldError("registerUser", "email",
                    "Email da ton tai. Vui long nhap Email khac"));
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            result.addError(new FieldError("registerUser", "password",
                    "Mat khau khong khop"));
        }

        if (result.hasErrors()) {
            return "user/sign-up";
        }

        User user = userService.createUser(userRequest);
        model.addAttribute("user", user);
        return "redirect:/login?success";
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
                                 @RequestParam("email") String email,
                                 BindingResult result) {
        try{
            emailService.sendEmailToReactivePassword(email);
        }catch (Exception ex){
            result.addError(new FieldError("user-forgot", "eamil",
                    "Email " + email + " not exists"));
            return "user/forgot-password";
        }
        return "redirect:/login?success";
    }


}
