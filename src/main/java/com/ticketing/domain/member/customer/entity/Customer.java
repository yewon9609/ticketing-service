package com.ticketing.domain.member.customer.entity;

import com.ticketing.domain.member.MemberInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private MemberInfo memberInfo;

  protected Customer() {
  }

  public Customer(MemberInfo memberInfo) {
    this.memberInfo = memberInfo;
  }

  public Long getId() {
    return id;
  }

  public MemberInfo getMemberInfo() {
    return memberInfo;
  }
}
