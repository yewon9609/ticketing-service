package com.ticketing.domain.member.token.dto.response;

public record TokensRes(
    String accessToken,
    String refreshToken
) {

}
