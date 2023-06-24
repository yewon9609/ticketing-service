package com.ticketing.domain.member.admin.dto.request;

import com.ticketing.domain.member.data.Email;
import com.ticketing.domain.member.data.MemberInfo;
import com.ticketing.domain.member.data.Role;
import com.ticketing.domain.member.admin.entity.Admin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record AdminCreateReq(

    @NotBlank
    @Size(max = 20)
    String name,

    @NotBlank
    @Pattern(regexp = "^[\\w-.]+@[\\w-]+.[\\w.]+$")
    @Size(max = 50)
    String email,

    @NotBlank
    @Size(max = 30)
    String password,

    @Min(1900)
    int birthYear,

    @NotBlank
    String businessLicense,

    @NotNull
    Role role

) {

  public Admin toAdmin(PasswordEncoder encoder) {
    return new Admin(
        new MemberInfo(
            name,
            new Email(email),
            encoder.encode(password),
            birthYear
        ),
        businessLicense,
        role
    );
  }

}
