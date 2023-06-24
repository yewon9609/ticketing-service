package com.ticketing.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.global.config.jwt.JwtAuthenticationFilter;
import com.ticketing.global.config.jwt.JwtTokenProvider.AdminJwtTokenProvider;
import com.ticketing.global.config.jwt.JwtTokenProvider.CustomerJwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfig {

  private SecurityConfig() {
  }

  @Configuration
  @EnableWebSecurity
  @Order(1)
  public static class CustomerSecurityConfig {

    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authEntryPoint;
    private final CustomerJwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public CustomerSecurityConfig(AuthenticationEntryPoint authEntryPoint,
        CustomerJwtTokenProvider jwtTokenProvider,
        ObjectMapper objectMapper) {
      this.authEntryPoint = authEntryPoint;
      this.jwtTokenProvider = jwtTokenProvider;
      this.objectMapper = objectMapper;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer1() {
      return web -> web.ignoring()
          .requestMatchers("/docs/rest-docs.html")
          .requestMatchers("/api/admins/signup")
          .requestMatchers("/api/customers/signup")
          .requestMatchers("/api/admins/login")
          .requestMatchers("/api/customers/login")
          .requestMatchers("/favicon.ico/**")
          ;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder1() {
      return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain customerFilterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
          .headers(authorize -> authorize.frameOptions(FrameOptionsConfig::disable))
          .authorizeHttpRequests(requests -> requests
              .requestMatchers("customers/signup", "customers/login").permitAll()
              .anyRequest().authenticated())
          .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
              UsernamePasswordAuthenticationFilter.class)
          .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint));

      return http.build();
    }
  }

  @EnableWebSecurity
  @Configuration
  @Order(2)
  public static class AdminSecurityConfig {

    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authEntryPoint;
    private final AdminJwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public AdminSecurityConfig(AdminJwtTokenProvider jwtTokenProvider,
        ObjectMapper objectMapper, AuthenticationEntryPoint authEntryPoint) {
      this.jwtTokenProvider = jwtTokenProvider;
      this.objectMapper = objectMapper;
      this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer2() {
      return web -> web.ignoring()
          .requestMatchers("/docs/rest-docs.html")
          .requestMatchers("/api/admins/signup")
          .requestMatchers("/api/customers/signup")
          .requestMatchers("/api/admins/login")
          .requestMatchers("/api/customers/login")
          .requestMatchers("/favicon.ico/**")
          ;
    }


    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
          .headers(authorize -> authorize.frameOptions(FrameOptionsConfig::disable))
          .sessionManagement(
              session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(requests -> requests
              .requestMatchers("admins/signup", "admins/login")
              .permitAll()
              .requestMatchers("/admins/**").hasAnyRole("PERFORMANCE_MANAGER", "VENUE_MANAGER")
              .requestMatchers(HttpMethod.POST, "/api/performances").hasRole("PERFORMANCE_MANAGER")
              .requestMatchers(HttpMethod.POST, "/api/venues").hasRole("VENUE_MANAGER")
              .requestMatchers("/api/performances/**").hasRole("PERFORMANCE_MANAGER")
              .requestMatchers("/api/venues/**").hasRole("VENUE_MANAGER")
              .anyRequest()
              .authenticated())
          .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
              UsernamePasswordAuthenticationFilter.class)
          .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint));
      return http.build();
    }
  }

}
