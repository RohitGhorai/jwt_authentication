package com.auth.authentication.security;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.authentication.config.AppConstant;
import com.auth.authentication.entity.RefreshToken;
import com.auth.authentication.entity.User;
import com.auth.authentication.exception.ApiException;
import com.auth.authentication.repo.RefreshTokenRepo;
import com.auth.authentication.repo.UserRepo;

@Service
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepo refreshTokenRepo;

  @Autowired
  private UserRepo userRepo;

  public RefreshToken createRefreshToken(String userName) {
    User user = this.userRepo.findByEmail(userName).get();
    RefreshToken refreshToken = user.getRefreshToken();
    if(refreshToken == null) {
        refreshToken = RefreshToken.builder()
            .refreshToken(UUID.randomUUID().toString())
            .expiry(Instant.now().plusMillis(AppConstant.REFRESH_TOKEN_VALIDITY))
            .user(this.userRepo.findByEmail(userName).get())
            .build();
    } else {
        refreshToken.setExpiry(Instant.now().plusMillis(AppConstant.REFRESH_TOKEN_VALIDITY));
    }
    user.setRefreshToken(refreshToken);
    refreshTokenRepo.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyRefreshToken(String refreshToken) {
    RefreshToken refreshToken1 = this.refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(() -> new ApiException("Refresh token not found with " + refreshToken + " this name !"));
    if (refreshToken1.getExpiry().compareTo(Instant.now()) < 0) {
      refreshTokenRepo.delete(refreshToken1);
      throw new ApiException("Refresh token was expired. Please make a new signIn request");
    }
    return refreshToken1;
  }

}