package com.auth.authentication.payloads;

import com.auth.authentication.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {
    private String jwtToken;
    private String refreshToken;
    private User user;
}
