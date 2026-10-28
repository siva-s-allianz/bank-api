package com.siva.bank_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CsrfDemoSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain csrfDemoChain(HttpSecurity http)
            throws Exception {

        http
            .securityMatcher("/demo/csrf/**")
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .csrf(csrf -> { });

        return http.build();
    }
}