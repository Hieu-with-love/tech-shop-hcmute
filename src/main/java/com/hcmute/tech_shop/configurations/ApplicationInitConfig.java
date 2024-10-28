package com.hcmute.tech_shop.configurations;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.enums.Role;
import com.hcmute.tech_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        var roles = new HashSet<String>();
        roles.add(Role.ADMIN.name());
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                com.hcmute.tech_shop.entities.User user = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .firstName("admin")
                        .lastName("tech system")
                        .isActive(true)
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.info("Default admin user created with password:admin, please change it");
            }
        };
    }
}
