package com.ticketing.domain.performance.entity;

import static com.ticketing.global.exception.ErrorCode.NOT_PERFORMANCE_MANAGER;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.entity.Role;
import com.ticketing.domain.member.admin.exception.UnauthorizedException;
import com.ticketing.domain.venue.entity.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Performance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false)
  private String thumbnail;

  @Embedded
  private Schedule schedule;

  @Column(nullable = false)
  private int viewingAge;

  @Column(nullable = false)
  private int seatPrice;

  @Column(nullable = false)
  private int capacity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Venue venue;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Admin admin;

  protected Performance() {
  }

  public Performance(String title, String thumbnail, Schedule schedule, int viewingAge,
      int seatPrice, int capacity, Venue venue, Admin admin) {
    checkRole(admin);
    this.title = title;
    this.thumbnail = thumbnail;
    this.schedule = schedule;
    this.viewingAge = viewingAge;
    this.seatPrice = seatPrice;
    this.capacity = capacity;
    this.venue = venue;
    this.admin = admin;
  }

  private void checkRole(Admin admin) {
    if (!admin.getRole().equals(Role.PERFORMANCE_MANAGER)) {
      throw new UnauthorizedException(NOT_PERFORMANCE_MANAGER);
    }
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

  public int getSeatPrice() {
    return seatPrice;
  }

  public Venue getVenue() {
    return venue;
  }

  public Admin getAdmin() {
    return admin;
  }

  public int getCapacity() {
    return capacity;
  }
}
