package com.ticketing.domain.performance.entity;

import static com.ticketing.global.exception.ErrorCode.NOT_AVAILABLE_AGE;

import com.ticketing.domain.BaseTimeEntity;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.performance.exception.NotAvalilableAgeException;
import com.ticketing.domain.venue.entity.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Performance extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Lob
  @Column(nullable = false)
  private String thumbnail;

  @Embedded
  private Schedule schedule;

  @Column(nullable = false)
  private int viewingAge;

  @Embedded
  private Seat seat;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Venue venue;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Admin admin;

  protected Performance() {
  }

  public Performance(String title, String thumbnail, Schedule schedule, int viewingAge, Seat seat,
      Venue venue, Admin admin) {
    this.title = title;
    this.thumbnail = thumbnail;
    this.schedule = schedule;
    this.viewingAge = viewingAge;
    this.seat = seat;
    this.venue = venue;
    this.admin = admin;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  public int getViewingAge() {
    return viewingAge;
  }

  public Seat getSeat() {
    return seat;
  }

  public Venue getVenue() {
    return venue;
  }

  public Admin getAdmin() {
    return admin;
  }

  public void checkPossibleViewingAge(int age) {
    if (viewingAge > age) {
      throw new NotAvalilableAgeException(NOT_AVAILABLE_AGE);
    }
  }

}