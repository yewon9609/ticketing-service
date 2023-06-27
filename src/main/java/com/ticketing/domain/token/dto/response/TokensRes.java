package com.ticketing.domain.token.dto.response;

public record TokensRes(
    String accessToken,
    String refreshToken
) {

}
