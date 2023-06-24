package com.ticketing.domain.member.customer.entity;

import com.ticketing.domain.BaseTimeEntity;
import com.ticketing.domain.member.data.MemberInfo;
import com.ticketing.domain.member.data.Role;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private MemberInfo memberInfo;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  protected Customer() {
  }

  public Customer(MemberInfo memberInfo) {
    this.memberInfo = memberInfo;
    this.role = Role.ROLE_CUSTOMER;
  }

  public Long getId() {
    return id;
  }

  public MemberInfo getMemberInfo() {
    return memberInfo;
  }

  public Role getRole() {
    return role;
  }

}
