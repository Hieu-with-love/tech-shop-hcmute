package com.hcmute.tech_shop.configurations;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_POST_ENDPOINTS = { "/api/auth/log-in", "/api/users/**"
    };
    private final String[] PUBLIC_GET_ENDPOINTS = {"/api/auth/user/details", "/login", "/register", "/user/home",
        "/log-out", "/forgot-password", "/api/users/**"
    };

    @Value("${jwt.signedKey}")
    @NonFinal
    private String signedKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
            request.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
                    .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
                    .requestMatchers("/user/assets/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/users").hasAuthority("SCOPE_ADMIN")
                    .anyRequest().authenticated()
            )
                .formLogin(form ->
                    form.loginPage("/login").permitAll()
                )
                .logout(logout ->
                    logout.logoutUrl("/log-out")
                            .logoutSuccessHandler(customLogout())
                            .permitAll()
                )
        ;
        // you can custom SCOPE_ADMIN -> ROLE_ADMIN
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    private LogoutSuccessHandler customLogout() {
        // set status 200, instead redirect to login
        return (req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKetSpec = new SecretKeySpec(signedKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKetSpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
