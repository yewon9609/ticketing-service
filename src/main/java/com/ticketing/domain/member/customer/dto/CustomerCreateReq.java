package com.ticketing.domain.member.customer.dto;

import com.ticketing.domain.member.MemberInfo;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.entity.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreateReq(

    @NotBlank
    String name,
    @NotBlank
    @Pattern(regexp = "^[\\w-.]+@[\\w-]+.[\\w.]+$")
    String email,
    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$")
    String password,
    @NotBlank
    String birthYear
) {

  public Customer toEntity() {
    return new Customer(new MemberInfo(name, new Email(email), password, birthYear));
  }
}
