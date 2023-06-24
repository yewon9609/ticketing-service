package com.ticketing.domain.member.admin.service;

import com.ticketing.domain.member.admin.dto.request.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.request.AdminLoginReq;
import com.ticketing.domain.member.admin.dto.response.AdminCreateRes;
import com.ticketing.domain.member.admin.dto.response.AdminLoginRes;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.repository.AdminRepository;
import com.ticketing.global.config.jwt.JwtTokenProvider;
import com.ticketing.global.config.jwt.JwtTokenProvider.AdminJwtTokenProvider;
import com.ticketing.infra.redis.util.RedisUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;
  private final RedisUtil redisUtil;
  private final JwtTokenProvider jwtTokenProvider;

  public AdminService(AdminRepository repository, PasswordEncoder encoder, RedisUtil redisUtil,
      AdminJwtTokenProvider adminJwtTokenProvider) {
    this.adminRepository = repository;
    this.passwordEncoder = encoder;
    this.redisUtil = redisUtil;
    this.jwtTokenProvider = adminJwtTokenProvider;
  }

  @Transactional
  public AdminCreateRes create(AdminCreateReq adminCreateReq) {
    Admin admin = adminRepository.save(adminCreateReq.toAdmin(passwordEncoder));
    return AdminCreateRes.from(admin);
  }

  public Admin getBy(Long id) {
    return adminRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  private Admin getByEmail(String email) {
    return adminRepository.findByEmail(email)
        .orElseThrow(EntityNotFoundException::new);
  }

  public AdminLoginRes login(AdminLoginReq loginReq) {
    Admin admin = getByEmail(loginReq.email());
    admin.getMemberInfo().checkPassword(passwordEncoder, loginReq.password());

    String accessToken = jwtTokenProvider.createAccessToken(
        admin.getId(),
        jwtTokenProvider.getAccessTokenSecretKey()
    );

    String refreshToken = jwtTokenProvider.createRefreshToken(
        jwtTokenProvider.getRefreshTokenSecretKey()
    );

    redisUtil.setDataWithExpire(String.valueOf(admin.getId()), refreshToken);

    return new AdminLoginRes(accessToken, refreshToken);
  }

}
