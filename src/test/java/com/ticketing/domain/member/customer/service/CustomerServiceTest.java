package com.ticketing.domain.member.customer.service;

import static com.ticketing.testFixture.TestFixture.createCustomerWithId;
import static com.ticketing.testFixture.TestFixture.createCustomerCreateReq;
import static com.ticketing.testFixture.TestFixture.createCustomerLoginReq;
import static com.ticketing.testFixture.TestFixture.createTokenRes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.domain.member.customer.dto.request.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.request.CustomerLoginReq;
import com.ticketing.domain.member.customer.dto.response.CustomerCreateRes;
import com.ticketing.domain.member.customer.dto.response.CustomerLoginRes;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import com.ticketing.domain.member.token.dto.response.TokensRes;
import com.ticketing.domain.member.token.service.CustomerJwtService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service 클래스")
class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private CustomerJwtService jwtService;
  @InjectMocks
  private CustomerService customerService;

  @DisplayName("고객을 생성할 수 있다")
  @Test
  void test_create() {
    Customer customer = createCustomerWithId();
    CustomerCreateRes expectedCustomer = CustomerCreateRes.from(customer);
    CustomerCreateReq customerCreateReq = createCustomerCreateReq();

    when(passwordEncoder.encode(any())).thenReturn(customer.getMemberInfo().getPassword());
    when(customerRepository.save(any())).thenReturn(customer);

    CustomerCreateRes customerCreateRes = customerService.create(customerCreateReq);
    assertThat(customerCreateRes).isEqualTo(expectedCustomer);
  }

  @DisplayName("고객은 로그인 할 수 있다")
  @Test
  void test_login() {
    Customer customer = createCustomerWithId();
    CustomerLoginReq loginReq = createCustomerLoginReq();
    TokensRes tokensRes = createTokenRes();
    CustomerLoginRes expectedLoginRes = new CustomerLoginRes(
        tokensRes.accessToken(),
        tokensRes.refreshToken()
    );
    when(jwtService.createToken(any())).thenReturn(tokensRes);
    when(customerRepository.findByEmail(any())).thenReturn(Optional.of(customer));
    when(passwordEncoder.matches(any(), any())).thenReturn(true);

    CustomerLoginRes loginRes = customerService.login(loginReq);

    assertThat(loginRes).isEqualTo(expectedLoginRes);
  }

}