package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsServiceImpl implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.hcmute.tech_shop.entities.User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//
//        String role = "ROLE_" + user.getRole().getName().toUpperCase();
//        GrantedAuthority grantedAuth = new SimpleGrantedAuthority(role);
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Collections.singletonList(grantedAuth)
//        );
//    }
//}
