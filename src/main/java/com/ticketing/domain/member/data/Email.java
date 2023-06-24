package com.ticketing.domain.member.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

@Embeddable
public class Email {

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+.[\\w.]+$");
  private static final String OUT_OF_FORMAT = "이메일이 형식에 맞지 않습니다";

  @Column(nullable = false, length = 50, unique = true)
  private String mailPath;

  protected Email() {
  }

  public Email(String mailPath) {
    this.mailPath = validateEmail(mailPath);
  }

  private String validateEmail(String mailPath) {
    Assert.isTrue(EMAIL_PATTERN.matcher(mailPath).matches(), OUT_OF_FORMAT);
    return mailPath;
  }

  public String getMailPath() {
    return mailPath;
  }
}
