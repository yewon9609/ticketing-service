package com.ticketing.domain.member.customer.service;

import com.ticketing.domain.member.customer.dto.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.CustomerCreateRes;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public CustomerCreateRes create(CustomerCreateReq createReq) {
     Customer customer = customerRepository.save(createReq.toEntity());
     return CustomerCreateRes.from(customer);
  }
}
