package com.ticketing.domain.member.admin.service;

import static com.ticketing.testFixture.TestFixture.createPerformanceManager;
import static com.ticketing.testFixture.TestFixture.createPerformanceManagerCreateReq;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.domain.member.admin.dto.request.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.response.AdminCreateRes;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.repository.AdminRepository;
import com.ticketing.domain.member.token.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("Admin Service 클래스")
class AdminServiceTest {

  @Mock
  private AdminRepository adminRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;
  @InjectMocks
  private AdminService adminService;

  @DisplayName("공연 관리자를 생성할 수 있다")
  @Test
  void test_create() {
    Admin performanceManager = createPerformanceManager();
    AdminCreateRes expectedAdmin = AdminCreateRes.from(performanceManager);
    AdminCreateReq adminCreateReq = createPerformanceManagerCreateReq();

    when(passwordEncoder.encode(any())).thenReturn(performanceManager.getMemberInfo().getPassword());
    when(adminRepository.save(any())).thenReturn(performanceManager);

    AdminCreateRes adminCreateRes = adminService.create(adminCreateReq);
    assertThat(adminCreateRes).isEqualTo(expectedAdmin);
  }



}