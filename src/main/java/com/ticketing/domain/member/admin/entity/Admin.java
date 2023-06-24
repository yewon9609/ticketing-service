package com.ticketing.domain.member.admin.entity;

import com.ticketing.domain.BaseTimeEntity;
import com.ticketing.domain.member.data.MemberInfo;
import com.ticketing.domain.member.data.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private MemberInfo memberInfo;

  @Column(nullable = false, length = 15)
  private String businessLicense;

  @Enumerated(EnumType.STRING)
  private Role role;

  protected Admin() {
  }

  public Admin(MemberInfo memberInfo, String businessLicense, Role role) {
    this.memberInfo = memberInfo;
    this.businessLicense = businessLicense;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getBusinessLicense() {
    return businessLicense;
  }

  public Role getRole() {
    return role;
  }

  public MemberInfo getMemberInfo() {
    return memberInfo;
  }
}
