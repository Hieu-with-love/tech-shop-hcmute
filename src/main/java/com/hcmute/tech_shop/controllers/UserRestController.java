package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.ProfileDto;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping("/my-account/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileDto profileDto,
                                           BindingResult result){
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUsername(username);
            if (result.hasErrors()){
                Map<String, String> errors = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }
            userService.updateProfile(user, profileDto, result);
            return ResponseEntity.ok("Cập nhat profile thành công!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Cập nhat profile thất bại!");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody Map<String, String> params,
                                            BindingResult result){

        try{
            if (result.hasErrors()){
                Map<String, String> errors = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            params.put("username", username);
            userService.updatePassword(params, result);

            return ResponseEntity.ok("Change password success!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Đôi mật khẩu thất bại!");
        }
    }

}