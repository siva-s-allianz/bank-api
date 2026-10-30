package com.siva.bank_api;

import com.siva.bank_api.service.JwtTokenService;
import com.siva.bank_api.service.RefreshTokenService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService,
            RefreshTokenService refreshTokenService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken.unauthenticated(
                            request.username(),
                            request.password()
                        )
                    );

            String accessToken =
                    jwtTokenService.generateAccessToken(authentication);

            String refreshToken =
                    refreshTokenService.createRefreshToken(
                        authentication.getName()
                    );

            return ResponseEntity.ok(
                new RefreshTokenService.TokenPair(
                    accessToken,
                    refreshToken,
                    "Bearer",
                    900
                )
            );

        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestBody RefreshRequest request) {

        try {
            return ResponseEntity.ok(
                refreshTokenService.refresh(request.refreshToken())
            );

        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody RefreshRequest request) {

        try {
            refreshTokenService.revokeRefreshToken(
                request.refreshToken()
            );

            return ResponseEntity.noContent().build();

        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token"));
        }
    }

    public record LoginRequest(String username, String password) {}

    public record RefreshRequest(String refreshToken) {}
}