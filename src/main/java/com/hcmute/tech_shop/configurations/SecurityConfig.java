package com.hcmute.tech_shop.configurations;

//import com.hcmute.tech_shop.services.Impl.CustomUserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
//    LogoutSuccessHandler logoutSuccessHandler;
//    AuthenticationSuccessHandler authenticationSuccessHandler;
//    private final String[] PUBLIC_POST_ENDPOINTS = { "/api/auth/log-in", "/api/users/**"
//    };
//    private final String[] PUBLIC_GET_ENDPOINTS = {"/api/auth/user/details", "/login", "/register", "/user/home",
//        "/log-out", "/forgot-password", "/api/users/**"
//    };
//
//    @Value("${jwt.signedKey}")
//    @NonFinal
//    private String signedKey;

    private final String[] PUBLIC_ENDPOINTS = {"/register", "/forgot-password"};

//    private final CustomUserDetailsServiceImpl customUserDetailsService;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> request
//                    .requestMatchers("/user/assets/**").permitAll()
//                    .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
//                    .requestMatchers("/login").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//            )
//                .formLogin(form ->
//                    form.loginPage("/login")
//                            .successHandler(new CustomizeSuccessHandler())
//                            .failureHandler(new CustomAuthFailureHandler())
//                            .permitAll()
//                )
//                .logout(logout ->
//                    logout.logoutUrl("/log-out")
//                            .logoutSuccessHandler(logoutSuccessHandler)
//                            .permitAll()
//                );
//        return http.build();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
//        return authenticationManagerBuilder.build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter(){
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//
//        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
//        authConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return authConverter;
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKetSpec = new SecretKeySpec(signedKey.getBytes(), "HS512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKetSpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }

}
