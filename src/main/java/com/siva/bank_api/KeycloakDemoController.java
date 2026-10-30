package com.siva.bank_api;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keycloak-demo")
public class KeycloakDemoController {

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(
            @AuthenticationPrincipal Jwt jwt) {

        return Map.of(
            "message", "Keycloak JWT authentication successful",
            "username", jwt.getClaimAsString("preferred_username"),
            "subject", jwt.getSubject(),
            "issuer", jwt.getIssuer().toString()
        );
    }
}