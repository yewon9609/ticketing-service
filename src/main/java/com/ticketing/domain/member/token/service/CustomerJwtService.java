package com.ticketing.domain.member.token.service;

import com.ticketing.domain.member.token.dto.response.TokensRes;
import com.ticketing.global.config.jwt.JwtTokenProvider;
import com.ticketing.global.config.jwt.JwtTokenProvider.CustomerJwtTokenProvider;
import com.ticketing.infra.redis.util.RedisUtil;
import org.springframework.stereotype.Service;

@Service
public class CustomerJwtService implements JwtService{

  private final RedisUtil redisUtil;
  private final JwtTokenProvider jwtTokenProvider;

  public CustomerJwtService(RedisUtil redisUtil, CustomerJwtTokenProvider jwtTokenProvider) {
    this.redisUtil = redisUtil;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public TokensRes createToken(Long memberId) {
    String accessToken = createAccessToken(memberId);
    String refreshToken = createRefreshToken();
    redisUtil.setDataWithExpire(String.valueOf(memberId), refreshToken);

    return new TokensRes(accessToken, refreshToken);
  }

  private String createRefreshToken() {
    return jwtTokenProvider.createRefreshToken(
        jwtTokenProvider.getRefreshTokenSecretKey()
    );
  }

  private String createAccessToken(Long memberId) {
    return jwtTokenProvider.createAccessToken(
        memberId,
        jwtTokenProvider.getAccessTokenSecretKey()
    );
  }

}
