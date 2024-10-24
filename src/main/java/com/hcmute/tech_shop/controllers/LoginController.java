package com.hcmute.tech_shop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class LoginController {
    @GetMapping("log-in")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().build();
    }
}
