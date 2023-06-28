package com.ticketing.domain.member.token.service;

import com.ticketing.domain.member.token.dto.response.TokensRes;

public interface JwtService {

  TokensRes createToken(Long memberId);

}
