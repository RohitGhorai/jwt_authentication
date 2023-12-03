package com.auth.authentication.service;

import java.util.List;

import com.auth.authentication.entity.User;

public interface UserService {
    User register(User user);
    User getById(Integer userId);
    User update(User user, Integer userId);
    List<User> getAll();
    void deleteUser(Integer userId);
}
