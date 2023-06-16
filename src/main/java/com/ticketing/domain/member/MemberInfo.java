package com.ticketing.domain.member;

import com.ticketing.domain.member.customer.entity.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.util.Assert;

@Embeddable
public class MemberInfo {

  private static final int CURRENT_YEAR = LocalDate.now().getYear();

  @Column(nullable = false, length = 30)
  private String name;

  @Embedded
  private Email email;

  @Column(nullable = false, length = 30)
  private String password;

  @Column(nullable = false, length = 4)
  private String birthYear;

  protected MemberInfo() {
  }

  public MemberInfo(String name, Email email, String password, String birthYear) {
    validateBirthYear(birthYear);
    this.name = Objects.requireNonNull(name);
    this.email = Objects.requireNonNull(email);
    this.password = Objects.requireNonNull(password);
    this.birthYear = Objects.requireNonNull(birthYear);
  }

  private void validateBirthYear(String birthYear) {
    Assert.isTrue(CURRENT_YEAR <= Integer.parseInt(birthYear), "탄생년이 올바르지 않습니다");
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

  public String getBirthYear() {
    return birthYear;
  }

}
