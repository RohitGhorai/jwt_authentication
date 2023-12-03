package com.auth.authentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.authentication.config.AppConstant;
import com.auth.authentication.entity.User;
import com.auth.authentication.exception.ResourceNotFoundException;
import com.auth.authentication.repo.RoleRepo;
import com.auth.authentication.repo.UserRepo;
import com.auth.authentication.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public User register(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(this.roleRepo.findById(AppConstant.NORMAL_USER).get());
        return this.userRepo.save(user);
    }

    @Override
    public User getById(Integer userId) {
        return this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public User update(User user, Integer userId) {
        User getUser = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        getUser.setFirstName(user.getFirstName());
        getUser.setLastName(user.getLastName());
        getUser.setEmail(user.getEmail());
        getUser.setPassword(user.getPassword());
        return this.userRepo.save(getUser);
    }

    @Override
    public List<User> getAll() {
        return this.userRepo.findAll();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }
}
