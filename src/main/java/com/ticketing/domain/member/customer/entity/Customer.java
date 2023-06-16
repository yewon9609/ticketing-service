package com.ticketing.domain.member.customer.entity;

import com.ticketing.domain.member.MemberInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Customer {

  private static final int KOREA_AGE = 1;

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

  public int getAge() {
    int currentYear = LocalDate.now()
        .getYear();
    return currentYear - memberInfo.getBirthYear() + KOREA_AGE;
  }

}
