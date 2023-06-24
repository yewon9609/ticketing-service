package com.ticketing.domain.member.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdminLoginReq(

    @NotBlank
    @Pattern(regexp = "^[\\w-.]+@[\\w-]+.[\\w.]+$")
    String email,

    @NotBlank
    String password

) {

}
