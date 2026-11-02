package com.siva.bank_api.service;

import com.siva.bank_api.model.RefreshTokenEntity;
import com.siva.bank_api.repository.RefreshTokenRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(
            RefreshTokenRepository repository,
            JwtTokenService jwtTokenService,
            UserDetailsService userDetailsService) {

        this.repository = repository;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    public record TokenPair(
            String accessToken,
            String refreshToken,
            String tokenType,
            long expiresIn) {
    }

    public String createRefreshToken(String username) {

        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        String tokenHash = hashToken(rawToken);

        RefreshTokenEntity entity = new RefreshTokenEntity(
                tokenHash,
                username,
                Instant.now().plus(7, ChronoUnit.DAYS)
        );

        repository.save(entity);

        return rawToken;
    }

    @Transactional
    public TokenPair refresh(String submittedToken) {

        if (submittedToken == null || submittedToken.isBlank()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String hash = hashToken(submittedToken);

        RefreshTokenEntity existing = repository
                .findByTokenHash(hash)
                .orElseThrow(() ->
                    new BadCredentialsException("Invalid refresh token")
                );

        if (existing.isRevoked()
                || !existing.getExpiryDate().isAfter(Instant.now())) {
            throw new BadCredentialsException(
                "Refresh token expired or already used"
            );
        }

        UserDetails user = userDetailsService.loadUserByUsername(
                existing.getUsername()
        );

        var authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                    user,
                    null,
                    user.getAuthorities()
                );

        existing.revoke();
        repository.save(existing);

        String newRefreshToken = createRefreshToken(
                user.getUsername()
        );

        String newAccessToken =
                jwtTokenService.generateAccessToken(authentication);

        return new TokenPair(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                900
        );
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashed = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hashed);

        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "SHA-256 is unavailable", e
            );
        }
    }

    @Transactional
    public void revokeRefreshToken(String submittedToken) {

        if (submittedToken == null || submittedToken.isBlank()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String hash = hashToken(submittedToken);

        RefreshTokenEntity token = repository
                .findByTokenHash(hash)
                .orElseThrow(() ->
                    new BadCredentialsException("Invalid refresh token")
                );

        if (token.isRevoked()
                || !token.getExpiryDate().isAfter(Instant.now())) {
            throw new BadCredentialsException(
                "Refresh token expired or revoked"
            );
        }

        token.revoke();
        repository.save(token);
    }
}