package com.ticketing.domain.member.customer.dto;

import com.ticketing.domain.member.customer.entity.Customer;

public record CustomerCreateRes(
    Long id
) {

  public static CustomerCreateRes from(Customer customer) {
    return new CustomerCreateRes(customer.getId());
  }
}
