package com.ticketing.domain.member.admin.service;

import com.ticketing.domain.member.admin.dto.request.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.request.AdminLoginReq;
import com.ticketing.domain.member.admin.dto.response.AdminCreateRes;
import com.ticketing.domain.member.admin.dto.response.AdminLoginRes;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.repository.AdminRepository;
import com.ticketing.domain.member.exception.MemberNotFoundException;
import com.ticketing.domain.member.token.dto.response.TokensRes;
import com.ticketing.domain.member.token.service.AdminJwtService;
import com.ticketing.domain.member.token.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AdminService(AdminRepository repository, PasswordEncoder encoder,
      AdminJwtService jwtService) {
    this.adminRepository = repository;
    this.passwordEncoder = encoder;
    this.jwtService = jwtService;
  }

  @Transactional
  public AdminCreateRes create(AdminCreateReq adminCreateReq) {
    Admin admin = adminRepository.save(adminCreateReq.toAdmin(passwordEncoder));
    return AdminCreateRes.from(admin);
  }

  public Admin getById(Long id) {
    return adminRepository.findById(id)
        .orElseThrow(MemberNotFoundException::new);
  }

  private Admin getByEmail(String email) {
    return adminRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);
  }

  public AdminLoginRes login(AdminLoginReq loginReq) {
    Admin admin = getByEmail(loginReq.email());
    admin.getMemberInfo().checkPassword(passwordEncoder, loginReq.password());

    TokensRes token = jwtService.createToken(admin.getId());

    return new AdminLoginRes(token.accessToken(), token.refreshToken());
  }

}
