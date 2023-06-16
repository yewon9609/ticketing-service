package com.ticketing.domain.member.admin.dto.response;

import com.ticketing.domain.member.admin.entity.Admin;

public record AdminCreateRes(

    Long id

) {

  public static AdminCreateRes from(Admin admin) {
    return new AdminCreateRes(admin.getId());
  }

}
