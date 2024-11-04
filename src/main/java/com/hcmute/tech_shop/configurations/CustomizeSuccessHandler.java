package com.hcmute.tech_shop.configurations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

//public class CustomizeSuccessHandler implements AuthenticationSuccessHandler {
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        HttpSession session = request.getSession();
//        session.setAttribute("username", authentication.getName());
//        if (roles.contains("ROLE_ADMIN")) {
//            response.sendRedirect("/admin/dashboard");
//        } else if (roles.contains("ROLE_USER")) {
//            response.sendRedirect("/user/home");
//        } else {
//            response.sendRedirect("/login?error");
//        }
//    }
//}
