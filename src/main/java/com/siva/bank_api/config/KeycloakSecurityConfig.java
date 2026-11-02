package com.siva.bank_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class KeycloakSecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain keycloakSecurityFilterChain(
            HttpSecurity http) throws Exception {

        JwtDecoder keycloakDecoder = JwtDecoders.fromIssuerLocation(
            "http://localhost:8081/realms/bank-realm"
        );

        http
            .securityMatcher("/api/keycloak-demo/**")

            .csrf(csrf -> csrf.disable())

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )

            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(keycloakDecoder)
                )
            );

        return http.build();
    }
}