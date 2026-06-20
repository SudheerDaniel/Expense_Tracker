package com.example.springbootmvcexample.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // the actual refresh token string (a JWT) issued to the user
    @Column(nullable = false, unique = true, length = 512)
    private String token;

    // which user this refresh token belongs to
    @Column(nullable = false)
    private Long userId;


    // where this refresh token expires - used to reject expired token even if the row still exists
    @Column(nullable = false)
    private Instant expiryDate;

}

