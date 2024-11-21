package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.configurations.CustomUserDetails;
import com.hcmute.tech_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.hcmute.tech_shop.entities.User user = userRepository.findByUsername(username)
                .orElse(new com.hcmute.tech_shop.entities.User());

        if (user.getId() == null){
            throw new BadCredentialsException("Invalid username");
        }

        String role = "ROLE_" + user.getRole().getName().toUpperCase();
        GrantedAuthority grantedAuth = new SimpleGrantedAuthority(role);

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                Collections.singletonList(grantedAuth)
        );
    }
}
