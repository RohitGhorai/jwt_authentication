package com.auth.authentication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.authentication.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer>{
    Optional<RefreshToken> findByRefreshToken(String token);
}