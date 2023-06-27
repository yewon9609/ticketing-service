package com.ticketing.domain.token.service;

import com.ticketing.domain.token.dto.response.TokensRes;
import com.ticketing.global.config.jwt.JwtTokenProvider;
import com.ticketing.global.config.jwt.JwtTokenProvider.CustomerJwtTokenProvider;
import com.ticketing.infra.redis.util.RedisUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final RedisUtil redisUtil;
  private final CustomerJwtTokenProvider jwtTokenProvider;

  public JwtService(RedisUtil redisUtil, CustomerJwtTokenProvider jwtTokenProvider) {
    this.redisUtil = redisUtil;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public TokensRes createToken(Long customerId) {
    String accessToken = createAccessToken(customerId);
    String refreshToken = createRefreshToken();
    redisUtil.setDataWithExpire(String.valueOf(customerId), refreshToken);

    return new TokensRes(accessToken, refreshToken);
  }

  private String createRefreshToken() {
    return jwtTokenProvider.createRefreshToken(
        jwtTokenProvider.getRefreshTokenSecretKey()
    );
  }

  private String createAccessToken(Long customerId) {
    return jwtTokenProvider.createAccessToken(
        customerId,
        jwtTokenProvider.getAccessTokenSecretKey()
    );
  }

}
