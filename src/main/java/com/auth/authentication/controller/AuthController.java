package com.auth.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.auth.authentication.entity.RefreshToken;
import com.auth.authentication.entity.User;
import com.auth.authentication.exception.ApiException;
import com.auth.authentication.payloads.*;
import com.auth.authentication.repo.UserRepo;
import com.auth.authentication.security.JwtTokenHelper;
import com.auth.authentication.security.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public JwtAuthResponse createToken(@RequestBody JwtAuthRequest request){
        this.authenticate(request.getUserName(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(userDetails.getUsername());
        // JwtAuthResponse response = new JwtAuthResponse();
        // response.setJwtToken(token);
        // response.setRefreshToken(refreshToken.getRefreshToken());
        // response.setUser(this.userRepo.findByEmail(request.getUserName()).get());
        User user = this.userRepo.findByEmail(request.getUserName()).get();
        return JwtAuthResponse.builder()
            .jwtToken(token)
            .refreshToken(refreshToken.getRefreshToken())
            .user(user)
            .build();
    }

    @PostMapping("/refresh")
    public JwtAuthResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        RefreshToken refreshToken = this.refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        String token = this.jwtTokenHelper.generateToken(user);
        return JwtAuthResponse.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .jwtToken(token)
                .user(user)
                .build();
    }

    private void authenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            throw new ApiException("Incorrect username or password !!");
        }
    }
}
