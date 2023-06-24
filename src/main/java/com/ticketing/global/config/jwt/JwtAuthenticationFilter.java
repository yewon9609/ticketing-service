package com.ticketing.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.global.exception.BusinessException;
import com.ticketing.global.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final JwtTokenProvider jwtTokenProvider;
  private final ObjectMapper objectMapper;

  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String token = request.getHeader("AccessToken");

    try {
      if (token != null && jwtTokenProvider.validateAccessToken(token,
          jwtTokenProvider.getAccessTokenSecretKey(), log)) {
        String accessToken = jwtTokenProvider.removeBearer(token);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (BusinessException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
      response.setCharacterEncoding("UTF-8");
      objectMapper.writeValue(response.getWriter(),
          new ErrorResponse(e.getErrorCode().getMessage()));
    }

    filterChain.doFilter(request, response);
  }
}
