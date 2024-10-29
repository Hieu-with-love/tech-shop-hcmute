package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserDTO;
import com.hcmute.tech_shop.dtos.responses.AuthResponse;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.EmailService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRestController {
    UserService userService;
    EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid UserDTO userDTO,
            BindingResult binResult
    ) {
        Map<String, String> errors = new HashMap<>();
        // check has error while validate data
        if (binResult.hasErrors()) {
            binResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }
        // check have already username in use ?
        if (userService.existsUsername(userDTO.getUsername())){
            errors.put("username", "Username already in use");
            return ResponseEntity.badRequest().body(errors);
        }
        // save user when pass error
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("token") String token){
        boolean isSuccess = userService.verifyToken(token);
        return ResponseEntity.ok(AuthResponse.builder()
                .authenticated(isSuccess)
                .build());
    }

    @GetMapping("/log-out")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request){
        String email = request.get("email");
        if (!userService.existsEmail(email)){
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("msg", "Email không tồn tại trong hệ thống"));
        }

        emailService.sendEmailToReactivePassword(email);

        return ResponseEntity.ok(
                Collections.singletonMap("msg","New password has been issued. Go to your email to get."));
    }
}
