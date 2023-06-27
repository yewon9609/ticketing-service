package com.ticketing.domain.performance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Seat {

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private int capacity;

  public Seat() {
  }

  public Seat(int price, int capacity) {
    this.price = price;
    this.capacity = capacity;
  }

  public int getPrice() {
    return price;
  }

  public int getCapacity() {
    return capacity;
  }

  public int updateCapacity(int capacity) {
    return this.capacity = capacity;
  }

}
