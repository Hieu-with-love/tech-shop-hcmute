package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.Impl.EmailServiceImpl;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.validation.Valid;
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

import java.time.LocalDate;

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
                           @Valid @ModelAttribute("userRegister") UserRequest userRequest,
                           BindingResult result
                           ){
        boolean is = userService.createUser(userRequest, result);

        if (result.hasErrors()) {
            return "user/sign-up";
        }

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
