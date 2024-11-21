package com.hcmute.tech_shop.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;
    private boolean isActive;

    public CustomUserDetails(String username, String password, boolean isActive, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.isActive = isActive;
    }

}
