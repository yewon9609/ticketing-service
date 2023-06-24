package com.ticketing.domain.member.customer.dto.request;

import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.data.MemberInfo;
import com.ticketing.domain.member.data.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record CustomerCreateReq(

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
    int birthYear

) {

  public Customer toCustomer(PasswordEncoder encoder) {
    return new Customer(
        new MemberInfo(name, new Email(email), encoder.encode(password), birthYear));
  }

}
