package com.ticketing.domain.member.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerLoginReq(

    @NotBlank
    @Pattern(regexp = "^[\\w-.]+@[\\w-]+.[\\w.]+$")
    String email,

    @NotBlank
    String password
) {

}
