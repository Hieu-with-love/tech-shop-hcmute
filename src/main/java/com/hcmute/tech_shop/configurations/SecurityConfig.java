package com.hcmute.tech_shop.configurations;

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

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_POST_ENDPOINTS = { "/api/auth/token", "/api/auth/introspect", "/api/auth/log-in",
            "/api/users/register"
    };
    private final String[] PUBLIC_GET_ENDPOINTS = {"/api/auth/user/details", "/login", "/register", "/user/home"};

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
                    logout.logoutUrl("/logout").permitAll()
                )
        ;

        // you can custom SCOPE_ADMIN -> ROLE_ADMIN
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );

        http.csrf(AbstractHttpConfigurer::disable);

//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((request) -> {
//                    request.anyRequest().permitAll(); // Cho phép tất cả các request
//                });

        return http.build();
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
