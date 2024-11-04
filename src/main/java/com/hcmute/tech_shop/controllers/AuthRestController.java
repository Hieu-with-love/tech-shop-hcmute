package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.AuthRequest;
import com.hcmute.tech_shop.dtos.responses.AuthResponse;
import com.hcmute.tech_shop.services.Impl.AuthServiceImpl;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthRestController {
    AuthServiceImpl authenticationService;
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        AuthResponse result = authenticationService.authenticate(request);
        return ResponseEntity.ok(result);
    }
}
