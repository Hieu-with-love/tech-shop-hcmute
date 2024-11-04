package com.hcmute.tech_shop.configurations;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class AuthHandler {

//    @Bean
//    public LogoutSuccessHandler customLogout() {
//        // set status 200, instead redirect to login
//        return (req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK);
//    }

//    @Bean
//    public AuthenticationSuccessHandler customLogin(){
//        return (req, res, authentication) -> {
//            String role = authentication.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .filter(auth -> auth.equals("ROLE_ADMIN") || auth.equals("ROLE_USER"))
//                    .findFirst()
//                    .orElse("ROLE_USER");
//
//            if ("ROLE_ADMIN".equals(role)) {
//                res.sendRedirect("/admin/dashboard");
//            }else{
//                res.sendRedirect("/user/home");
//            }
//        };
//    }

}
