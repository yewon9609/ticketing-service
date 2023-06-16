package com.ticketing.presentation.admin;

import com.ticketing.domain.member.admin.dto.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.AdminCreateRes;
import com.ticketing.domain.member.admin.service.AdminService;
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

  @PostMapping
  public ResponseEntity<AdminCreateRes> signUp(
      @RequestBody AdminCreateReq adminCreateReq
  ) {
    AdminCreateRes adminCreateRes = adminService.create(adminCreateReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(adminCreateRes);
  }


}
