package com.ticketing.domain.venue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

@Entity
public class Venue {

  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
  private static final String OUT_OF_FORMAT = "전화번호는 숫자만 입력 가능합니다";
  private static final String LESS_THAN_ZERO = "총 좌석수는 0보다 커야합니다";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false, length = 15)
  private String phoneNumber;

  @Column(nullable = false)
  private int totalSeats;

  protected Venue() {
  }

  public Venue(String name, String address, String phoneNumber, int totalSeats) {
    validPhoneNumber(phoneNumber);
    validTotalSeats(totalSeats);
    this.name = name;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.totalSeats = totalSeats;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public int getTotalSeats() {
    return totalSeats;
  }

  public void validPhoneNumber(String phoneNumber) {
    Assert.isTrue(PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches(), OUT_OF_FORMAT);
  }

  public void validTotalSeats(int totalSeats) {
    Assert.isTrue(totalSeats > 0, LESS_THAN_ZERO);
  }
}
