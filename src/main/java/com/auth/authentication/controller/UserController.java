package com.auth.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth.authentication.entity.User;
import com.auth.authentication.payloads.ApiResponse;
import com.auth.authentication.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        return new ResponseEntity<>(this.userService.register(user), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        return new ResponseEntity<>(this.userService.getById(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> getUserById(@RequestBody User user, @PathVariable Integer userId){
        return new ResponseEntity<>(this.userService.update(user, userId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }
}
