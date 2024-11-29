package com.hcmute.tech_shop.configurations;

import com.hcmute.tech_shop.dtos.requests.RoleRequest;
import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.repositories.UserRepository;
import com.hcmute.tech_shop.services.Impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final RoleServiceImpl roleService;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {

        return args -> {
            // Tạo các role mặc định nếu chưa tồn tại
            String[] roleNames = {"user", "admin", "manage", "shipper"};
            for (String roleName : roleNames) {
                if (roleService.getRoleByName(roleName) == null) {
                    RoleRequest role = RoleRequest.builder()
                            .name(roleName)
                            .build();
                    roleService.createRole(role);
                    log.info("Default role created: " + roleName);
                }
            }

            // Tạo tài khoản admin mặc định nếu chưa tồn tại

            if(userRepository.findByUsername("admin").isEmpty()){
                Role roleAdmin = roleService.getRoleByName("admin");
                com.hcmute.tech_shop.entities.User user = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder().encode("admin"))
                        .firstName("admin")
                        .lastName("tech system")
                        .isActive(true)
                        .role(roleAdmin)
                        .build();
                userRepository.save(user);
                log.info("Default admin user created with password:admin, please change it");
            }

            for (int i = 0; i < 10; i++){
                if (userRepository.findByUsername("user " + i).isEmpty()){
                    Role roleUser = roleService.getRoleByName("user");
                    com.hcmute.tech_shop.entities.User user = User.builder()
                            .username("user" + i)
                            .email("user" + i + "@gmail.com")
                            .password(passwordEncoder().encode("user"))
                            .firstName("system")
                            .lastName("user" + i)
                            .isActive(true)
                            .role(roleUser)
                            .build();
                    userRepository.save(user);
                    log.info("Default user created with username = {} password = {}", user.getUsername(), user.getPassword());
                }
            }
        };
    }
}
