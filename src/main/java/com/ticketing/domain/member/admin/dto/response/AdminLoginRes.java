package com.ticketing.domain.member.admin.dto.response;

public record AdminLoginRes(

    String accessToken,

    String refreshToken

) {

}
