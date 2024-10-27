package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserDTO;
import com.hcmute.tech_shop.entities.User;
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
        if (userService.existsUser(userDTO.getEmail())){
            errors.put("email", "Email address already in use");
            return ResponseEntity.badRequest().body(errors);
        }
        // save user when pass error
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/log-out")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successfully");
    }
}
