package com.ticketing.domain.member;

import com.ticketing.domain.member.customer.entity.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.util.Assert;

@Embeddable
public class MemberInfo {

  private static final int CURRENT_YEAR = LocalDate.now().getYear();
  private static final String BIRTH_YEAR_ERROR_MSG = "탄생년이 올바르지 않습니다";

  @Column(nullable = false, length = 30)
  private String name;

  @Embedded
  private Email email;

  @Column(nullable = false, length = 30)
  private String password;

  @Column(nullable = false)
  @Min(1900)
  private int birthYear;

  protected MemberInfo() {
  }

  public MemberInfo(String name, Email email, String password, int birthYear) {
    validateBirthYear(birthYear);
    this.name = Objects.requireNonNull(name);
    this.email = Objects.requireNonNull(email);
    this.password = Objects.requireNonNull(password);
    this.birthYear = birthYear;
  }

  private void validateBirthYear(int birthYear) {
    Assert.isTrue(CURRENT_YEAR >= birthYear, BIRTH_YEAR_ERROR_MSG);
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public int getBirthYear() {
    return birthYear;
  }

}
