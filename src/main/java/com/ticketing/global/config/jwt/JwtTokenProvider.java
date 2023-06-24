package com.ticketing.global.config.jwt;

import com.ticketing.global.config.jwt.CustomUserDetailService.CustomAdminDetailService;
import com.ticketing.global.config.jwt.CustomUserDetailService.CustomCustomerDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Duration;
import java.util.Date;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface JwtTokenProvider {

  long ACCESS_TOKEN_EXPIRED_TIME = Duration.ofMinutes(10).toMillis();
  long REFRESH_TOKEN_EXPIRED_TIME = Duration.ofMinutes(30).toMillis();

  Authentication getAuthentication(String token);

  String getAccessTokenSecretKey();

  String getRefreshTokenSecretKey();

  default boolean validateAccessToken(String token, String accessTokenSecretKey, Logger log) {
    try {
      String accessToken = removeBearer(token);
      Jwts.parserBuilder()
          .setSigningKey(accessTokenSecretKey)
          .build()
          .parseClaimsJws(accessToken);
      return true;
    } catch (SignatureException ex) {
      log.error("유효하지 않은 JWT 서명");
      throw new RuntimeException("유효하지 않은 JWT 서명");
    } catch (MalformedJwtException ex) {
      log.error("유효하지 않은 JWT 토큰");
      throw new RuntimeException("유효하지 않은 JWT 토큰");
    } catch (ExpiredJwtException ex) {
      log.error("만료된 JWT 토큰");
      throw new RuntimeException("만료된 JWT 토큰");
    } catch (UnsupportedJwtException ex) {
      log.error("지원하지 않는 JWT 토큰");
      throw new RuntimeException("지원하지 않는 JWT 토큰");
    } catch (IllegalArgumentException ex) {
      log.error("비어있는 토큰");
      throw new RuntimeException("비어있는 토큰");
    }
  }

  default String createAccessToken(Long memberId, String accessTokenSecretKey) {
    Claims claims = Jwts.claims();
    claims.setSubject("ticketing");
    claims.put("memberId", memberId);

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, accessTokenSecretKey)
        .compact();
  }

  default String createRefreshToken(String refreshTokenSecretKey) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME);

    return Jwts.builder()
        .setSubject("ticketing")
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, refreshTokenSecretKey)
        .compact();
  }

  default String removeBearer(String bearerToken) {
    return bearerToken.substring("Bearer ".length());
  }

  default String getMemberId(String accessToken, String accessTokenSecretKey) {
    return Jwts.parserBuilder()
        .setSigningKey(accessTokenSecretKey)
        .build()
        .parseClaimsJws(accessToken)
        .getBody().get("memberId").toString();
  }

  @Component
  class CustomerJwtTokenProvider implements JwtTokenProvider {

    private final String accessTokenSecretKey;
    private final String refreshTokenSecretKey;
    private final CustomCustomerDetailService customerDetailService;

    public CustomerJwtTokenProvider(
        @Value("${spring.jwt.secret-key[0].accessToken}") String accessTokenSecretKey,
        @Value("${spring.jwt.secret-key[1].refreshToken}") String refreshTokenSecretKey,
        CustomCustomerDetailService customerDetailService) {
      this.accessTokenSecretKey = accessTokenSecretKey;
      this.refreshTokenSecretKey = refreshTokenSecretKey;
      this.customerDetailService = customerDetailService;
    }

    public Authentication getAuthentication(String token) {
      Long customerId = Long.parseLong(getMemberId(token, accessTokenSecretKey));
      UserDetails userDetails = customerDetailService.loadUserById(customerId);
      return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String getAccessTokenSecretKey() {
      return accessTokenSecretKey;
    }

    @Override
    public String getRefreshTokenSecretKey() {
      return refreshTokenSecretKey;
    }
  }

  @Component
  class AdminJwtTokenProvider implements JwtTokenProvider {

    private final String accessTokenSecretKey;
    private final String refreshTokenSecretKey;
    private final CustomAdminDetailService customAdminDetailService;

    public AdminJwtTokenProvider(
        @Value("${spring.jwt.secret-key[0].accessToken}") String accessTokenSecretKey,
        @Value("${spring.jwt.secret-key[1].refreshToken}") String refreshTokenSecretKey,
        CustomAdminDetailService customAdminDetailService) {
      this.accessTokenSecretKey = accessTokenSecretKey;
      this.refreshTokenSecretKey = refreshTokenSecretKey;
      this.customAdminDetailService = customAdminDetailService;
    }

    public Authentication getAuthentication(String token) {
      Long memberId = Long.parseLong(getMemberId(token, accessTokenSecretKey));
      UserDetails userDetails = customAdminDetailService.loadUserBy(memberId);
      return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getAccessTokenSecretKey() {
      return accessTokenSecretKey;
    }

    public String getRefreshTokenSecretKey() {
      return refreshTokenSecretKey;
    }
  }


}
