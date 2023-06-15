package com.ticketing.domain.member.admin.entity;

public enum Role {

  PERFORMANCE_MANAGER,
  VENUE_MANAGER;

  private String type;

  public String getType() {
    return type;
  }
}
