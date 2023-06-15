package com.ticketing.domain.performance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.util.Assert;

@Embeddable
public class Schedule {

  private static final String CANNOT_BEFORE_NOW = "공연 시작 날짜는 오늘날짜보다 이전일 수 없습니다";

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalTime startTime;

  protected Schedule() {
  }

  public Schedule(LocalDate startDate, LocalTime startTime) {
    validateDate(startDate);
    this.startDate = startDate;
    this.startTime = startTime;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void validateDate(LocalDate startDate) {
    Assert.isTrue(startDate.isAfter(LocalDate.now()), CANNOT_BEFORE_NOW);
  }

}
