package com.hcmute.tech_shop.configurations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        if (exception.getMessage().contains("not active")) {
            response.sendRedirect("/login?account_disable=true");
        }
        if (exception.getMessage().contains("Invalid username")){
            response.sendRedirect("/login?usernameNotFound=true");
        }
        else{
            response.sendRedirect("/login?error=true");
        }
    }
}
