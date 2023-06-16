package com.ticketing.domain.performance.entity;

import com.ticketing.domain.reservation.exception.NoMoreSeatsException;
import com.ticketing.global.exception.ErrorCode;
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

  public int subtractBy(int ticketCount) {
    if (capacity < ticketCount) {
      throw new NoMoreSeatsException(ErrorCode.NO_MORE_SEATS);
    }
    capacity = capacity - ticketCount;
    return capacity;
  }

}
