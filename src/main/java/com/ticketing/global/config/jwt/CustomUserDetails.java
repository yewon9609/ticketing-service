package com.ticketing.global.config.jwt;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.customer.entity.Customer;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails {

  private CustomUserDetails() {
  }

  public static class CustomerInfo implements UserDetails {

    private final Customer customer;

    public CustomerInfo(Customer customer) {
      this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(customer.getRole().toString()));
    }

    @Override
    public String getPassword() {
      return customer.getMemberInfo()
          .getPassword();
    }

    @Override
    public String getUsername() {
      return customer.getMemberInfo()
          .getMailPath();
    }

    public Long getId() {
      return customer.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
      return false;
    }

    @Override
    public boolean isAccountNonLocked() {
      return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return false;
    }

    @Override
    public boolean isEnabled() {
      return false;
    }
  }


  public static class AdminInfo implements UserDetails {

    private final Admin admin;

    public AdminInfo(Admin admin) {
      this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(admin.getRole().toString()));
    }

    @Override
    public String getPassword() {
      return admin.getMemberInfo()
          .getPassword();
    }

    @Override
    public String getUsername() {
      return admin.getMemberInfo()
          .getMailPath();
    }

    public Long getId() {
      return admin.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
      return false;
    }

    @Override
    public boolean isAccountNonLocked() {
      return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return false;
    }

    @Override
    public boolean isEnabled() {
      return false;
    }
  }

}
