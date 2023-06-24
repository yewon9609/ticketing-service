package com.ticketing.domain.member.customer.dto.response;

public record CustomerLoginRes(
    String accessToken,
    String refreshToken
) {

}
