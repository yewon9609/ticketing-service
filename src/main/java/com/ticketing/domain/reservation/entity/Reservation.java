package com.ticketing.domain.reservation.entity;

import com.ticketing.domain.BaseTimeEntity;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.performance.entity.Performance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Performance performance;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Customer customer;

  @Column(nullable = false)
  private int ticketCount;

  @Column(nullable = false)
  private int totalCost;

  protected Reservation() {
  }

  public Reservation(Performance performance, Customer customer, int ticketCount, int totalCost) {
    this.performance = performance;
    this.customer = customer;
    this.ticketCount = ticketCount;
    this.totalCost = totalCost;
  }

  public Performance getPerformance() {
    return performance;
  }

  public Customer getCustomer() {
    return customer;
  }

  public int getTicketCount() {
    return ticketCount;
  }

  public int getTotalCost() {
    return totalCost;
  }

  public Long getId() {
    return id;
  }

}
