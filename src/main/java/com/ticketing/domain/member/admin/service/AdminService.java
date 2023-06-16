package com.ticketing.domain.member.admin.service;

import com.ticketing.domain.member.admin.dto.request.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.response.AdminCreateRes;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

  private final AdminRepository adminRepository;

  public AdminService(AdminRepository repository) {
    this.adminRepository = repository;
  }

  @Transactional
  public AdminCreateRes create(AdminCreateReq adminCreateReq) {
    Admin admin = adminRepository.save(adminCreateReq.toEntity());
    return AdminCreateRes.from(admin);
  }

  public Admin getBy(Long id) {
    return adminRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
