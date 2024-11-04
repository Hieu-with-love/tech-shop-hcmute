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
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

//    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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
//                        .password(passwordEncoder.encode("admin"))
                        .firstName("admin")
                        .lastName("tech system")
                        .isActive(true)
                        .role(roleAdmin)
                        .build();
                userRepository.save(user);
                log.info("Default admin user created with password:admin, please change it");
            }
        };
    }
}
