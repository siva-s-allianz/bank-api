package com.siva.bank_api.model;

import java.time.Instant;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 64)
    private String tokenHash;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false)
    private Instant expiryDate;

    @Column (nullable = false)
    private boolean revoked = false;

    protected RefreshTokenEntity(){
    }

    public RefreshTokenEntity(
        String tokenHash,
        String username,
        Instant expiryDate
    ){
        this.tokenHash= tokenHash;
        this.username = username;
        this.expiryDate = expiryDate;
    }

    public Long getId(){
        return id;
    }

    public String getTokenHash(){
        return tokenHash;
    }

    public String getUsername(){
        return username;
    }

    public Instant getExpiryDate(){
        return expiryDate;
    }

    public boolean isRevoked(){
        return revoked;
    }

    public void revoke(){
        this.revoked =true;
    }
}
