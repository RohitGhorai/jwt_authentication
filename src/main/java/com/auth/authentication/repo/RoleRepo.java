package com.auth.authentication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.authentication.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
