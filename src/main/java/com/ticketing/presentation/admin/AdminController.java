package com.ticketing.presentation.admin;

import com.ticketing.domain.member.admin.dto.request.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.request.AdminLoginReq;
import com.ticketing.domain.member.admin.dto.response.AdminCreateRes;
import com.ticketing.domain.member.admin.dto.response.AdminLoginRes;
import com.ticketing.domain.member.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PostMapping("/signup")
  public ResponseEntity<AdminCreateRes> signUp(
      @RequestBody @Valid AdminCreateReq adminCreateReq
  ) {
    AdminCreateRes adminCreateRes = adminService.create(adminCreateReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(adminCreateRes);
  }

  @PostMapping("/login")
  public ResponseEntity<AdminLoginRes> login(@RequestBody @Valid AdminLoginReq adminLoginReq) {
    AdminLoginRes adminLoginRes = adminService.login(adminLoginReq);
    return ResponseEntity.ok(adminLoginRes);
  }

}
