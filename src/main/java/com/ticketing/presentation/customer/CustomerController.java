package com.ticketing.presentation.customer;

import com.ticketing.domain.member.customer.dto.request.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.request.CustomerLoginReq;
import com.ticketing.domain.member.customer.dto.response.CustomerCreateRes;
import com.ticketing.domain.member.customer.dto.response.CustomerLoginRes;
import com.ticketing.domain.member.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/signup")
  public ResponseEntity<CustomerCreateRes> signUp(
      @RequestBody @Valid CustomerCreateReq createReq
  ) {
    CustomerCreateRes createRes = customerService.create(createReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(createRes);
  }

  @PostMapping("/login")
  public ResponseEntity<CustomerLoginRes> login(@RequestBody @Valid CustomerLoginReq loginReq) {
    CustomerLoginRes customerLoginRes = customerService.login(loginReq);
    return ResponseEntity.ok(customerLoginRes);
  }

}
