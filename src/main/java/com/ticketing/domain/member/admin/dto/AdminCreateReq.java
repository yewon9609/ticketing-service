package com.ticketing.domain.member.admin.dto;

import com.ticketing.domain.member.MemberInfo;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.entity.Role;
import com.ticketing.domain.member.customer.entity.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AdminCreateReq(
    @NotBlank
    String name,
    @NotBlank
    @Pattern(regexp = "^[\\w-.]+@[\\w-]+.[\\w.]+$")
    String mailPath,
    @NotBlank
    String password,
    @NotBlank
    String birthYear,
    @NotBlank
    String businessLicense,
    @NotNull
    Role role
) {

  public Admin toEntity() {
    return new Admin(new MemberInfo(name, new Email(mailPath), password, birthYear),
        businessLicense, role);
  }
}
