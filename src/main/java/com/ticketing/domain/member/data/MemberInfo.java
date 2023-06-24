package com.ticketing.domain.member.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@Embeddable
public class MemberInfo {

  private static final int CURRENT_YEAR = LocalDate.now().getYear();
  private static final int KOREA_AGE = 1;
  private static final String BIRTH_YEAR_ERROR_MSG = "탄생년이 올바르지 않습니다";
  private static final String PASSWORD_NOT_MATCH = "패스워드가 일치하지 않습니다";

  @Column(nullable = false, length = 30)
  private String name;

  @Embedded
  private Email email;

  @Column(nullable = false)
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

  public void checkPassword(PasswordEncoder passwordEncoder, String inputPassword) {
    if (!passwordEncoder.matches(inputPassword, password))
      throw new IllegalArgumentException(PASSWORD_NOT_MATCH);
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public int getBirthYear() {
    return birthYear;
  }

  public int getAge() {
    int currentYear = LocalDate.now()
        .getYear();
    return currentYear - birthYear + KOREA_AGE;
  }

  public String getMailPath() {
    return email.getMailPath();
  }

}
