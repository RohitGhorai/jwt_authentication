package com.auth.authentication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.authentication.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
