package com.auth.authentication.payloads;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
