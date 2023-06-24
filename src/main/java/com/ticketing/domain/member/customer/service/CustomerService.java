package com.ticketing.domain.member.customer.service;

import com.ticketing.domain.member.customer.dto.request.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.request.CustomerLoginReq;
import com.ticketing.domain.member.customer.dto.response.CustomerCreateRes;
import com.ticketing.domain.member.customer.dto.response.CustomerLoginRes;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import com.ticketing.domain.member.exception.DuplicatedEmailException;
import com.ticketing.domain.member.exception.NotFoundMemberException;
import com.ticketing.global.config.jwt.JwtTokenProvider;
import com.ticketing.global.config.jwt.JwtTokenProvider.CustomerJwtTokenProvider;
import com.ticketing.global.exception.ErrorCode;
import com.ticketing.infra.redis.util.RedisUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final RedisUtil redisUtil;
  private final JwtTokenProvider jwtTokenProvider;

  public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
      RedisUtil redisUtil, CustomerJwtTokenProvider customerJwtTokenProvider) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
    this.redisUtil = redisUtil;
    this.jwtTokenProvider = customerJwtTokenProvider;
  }

  @Transactional
  public CustomerCreateRes create(CustomerCreateReq createReq) {
    checkDuplicatedEmail(createReq.email());
    Customer customer = customerRepository.save(createReq.toCustomer(passwordEncoder));
    return CustomerCreateRes.from(customer);
  }

  public Customer getBy(Long id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundMemberException(ErrorCode.NOT_FOUND_MEMBER));
  }

  private Customer getByEmail(String email) {
    return customerRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundMemberException(ErrorCode.NOT_FOUND_MEMBER));
  }

  public CustomerLoginRes login(CustomerLoginReq loginReq) {
    Customer customer = getByEmail(loginReq.email());
    customer.getMemberInfo().checkPassword(passwordEncoder, loginReq.password());

    String accessToken = jwtTokenProvider.createAccessToken(
        customer.getId(),
        jwtTokenProvider.getAccessTokenSecretKey()
    );
    String refreshToken = jwtTokenProvider.createRefreshToken(
        jwtTokenProvider.getRefreshTokenSecretKey()
    );

    redisUtil.setDataWithExpire(String.valueOf(customer.getId()), refreshToken);

    return new CustomerLoginRes(accessToken, refreshToken);
  }

  private void checkDuplicatedEmail(String email) {
    if (customerRepository.existsByMemberInfoEmailMailPath(email)) {
      throw new DuplicatedEmailException(ErrorCode.DUPLICATED_EMAIL);
    }
  }


}
