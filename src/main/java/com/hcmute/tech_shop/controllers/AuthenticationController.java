package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.AuthenticationRequest;
import com.hcmute.tech_shop.dtos.requests.IntrospectRequest;
import com.hcmute.tech_shop.dtos.responses.AuthenticationResponse;
import com.hcmute.tech_shop.dtos.responses.IntrospectResponse;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.classes.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .active(true)
                .build();
        User user2 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .firstName("Hieu")
                .lastName("Tran Trung")
                .active(true)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return ResponseEntity.ok(users);
    }
}
