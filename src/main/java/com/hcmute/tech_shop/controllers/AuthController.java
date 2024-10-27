package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.AuthenticationRequest;
import com.hcmute.tech_shop.dtos.requests.IntrospectRequest;
import com.hcmute.tech_shop.dtos.responses.AuthenticationResponse;
import com.hcmute.tech_shop.dtos.responses.IntrospectResponse;
import com.hcmute.tech_shop.dtos.responses.UserResponse;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.classes.AuthenticationService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseEntity.ok(result);
    }

    // endpoint get details of user
    @GetMapping("/user/details")
    public ResponseEntity<UserResponse> getUserDetails(Authentication auth){
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        UserResponse userResponse = UserResponse.builder()
                .email(username)
                .firstName(password)
                .build();
        return ResponseEntity.ok(userResponse);
    }

    // endpoint is /users
    @GetMapping("/users")
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
