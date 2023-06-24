package com.ticketing.domain.member;

public enum Role {
  ROLE_GUEST,
  ROLE_CUSTOMER,
  ROLE_PERFORMANCE_MANAGER,
  ROLE_VENUE_MANAGER;

  private String type;

  public String getType() {
    return type;
  }
}
