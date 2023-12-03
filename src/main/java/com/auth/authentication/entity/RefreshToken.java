package com.auth.authentication.entity;

import java.time.Instant;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tokenId;
    @Column(nullable = false, unique = true)
    private String refreshToken;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Instant expiry;
}
