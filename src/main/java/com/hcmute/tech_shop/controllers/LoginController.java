package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    UserService userService;

    // endpoint is /users
    @GetMapping("")
    public ResponseEntity<?> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User name: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> {
            log.info("GrantedAuthority: {}", grantedAuthority);
        });

        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("log-in")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().build();
    }

}
