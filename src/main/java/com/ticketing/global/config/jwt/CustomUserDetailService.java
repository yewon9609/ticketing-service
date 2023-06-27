package com.ticketing.global.config.jwt;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.repository.AdminRepository;
import com.ticketing.domain.member.customer.repository.CustomerRepository;
import com.ticketing.domain.member.exception.MemberNotFoundException;
import com.ticketing.global.config.jwt.CustomUserDetails.AdminInfo;
import com.ticketing.global.config.jwt.CustomUserDetails.CustomerInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class CustomUserDetailService {

  private CustomUserDetailService() {
  }

  @Service
  public static class CustomAdminDetailService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public CustomAdminDetailService(AdminRepository adminRepository) {
      this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Admin admin = adminRepository.findByEmail(email)
          .orElseThrow(MemberNotFoundException::new);
      return new AdminInfo(admin);
    }

    public UserDetails loadUserBy(Long id) {
      return new AdminInfo(adminRepository.findById(id)
          .orElseThrow(MemberNotFoundException::new));
    }
  }

  @Service
  public static class CustomCustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomCustomerDetailService(CustomerRepository customerRepository) {
      this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
      return new CustomerInfo(customerRepository.findByEmail(email)
          .orElseThrow(MemberNotFoundException::new));
    }

    public UserDetails loadUserById(Long id) {
      return new CustomerInfo(customerRepository.findById(id)
          .orElseThrow(MemberNotFoundException::new));
    }

  }

}
