package com.ticketing.domain.member.customer.service;

import com.ticketing.domain.member.customer.dto.request.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.request.CustomerLoginReq;
import com.ticketing.domain.member.customer.dto.response.CustomerCreateRes;
import com.ticketing.domain.member.customer.dto.response.CustomerLoginRes;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import com.ticketing.domain.member.exception.DuplicatedEmailException;
import com.ticketing.domain.member.exception.MemberNotFoundException;
import com.ticketing.domain.token.dto.response.TokensRes;
import com.ticketing.domain.token.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @Transactional
  public CustomerCreateRes create(CustomerCreateReq createReq) {
    checkDuplicatedEmail(createReq.email());
    Customer customer = customerRepository.save(createReq.toCustomer(passwordEncoder));
    return CustomerCreateRes.from(customer);
  }

  public Customer getById(Long id) {
    return customerRepository.findById(id)
        .orElseThrow(MemberNotFoundException::new);
  }

  private Customer getByEmail(String email) {
    return customerRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);
  }

  public CustomerLoginRes login(CustomerLoginReq loginReq) {
    Customer customer = getByEmail(loginReq.email());
    customer.getMemberInfo().checkPassword(passwordEncoder, loginReq.password());

    TokensRes token = jwtService.createToken(customer.getId());

    return new CustomerLoginRes(token.accessToken(), token.refreshToken());
  }

  private void checkDuplicatedEmail(String email) {
    if (customerRepository.existsByMemberInfoEmailMailPath(email)) {
      throw new DuplicatedEmailException();
    }
  }


}
