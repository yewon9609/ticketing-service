package com.ticketing.domain.member.customer.service;

import com.ticketing.domain.member.customer.dto.request.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.response.CustomerCreateRes;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public CustomerCreateRes create(CustomerCreateReq createReq) {
    Customer customer = customerRepository.save(createReq.toEntity());
    return CustomerCreateRes.from(customer);
  }

  public Customer getBy(Long customerId) {
    return customerRepository.findById(customerId)
        .orElseThrow(EntityNotFoundException::new);
  }

}
