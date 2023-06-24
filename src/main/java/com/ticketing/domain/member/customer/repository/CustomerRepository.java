package com.ticketing.domain.member.customer.repository;

import com.ticketing.domain.member.customer.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("select c "
      + "from Customer  c "
      + "where c.memberInfo.email.mailPath = :email")
  Optional<Customer> findByEmail(@Param("email") String email);

  boolean existsByMemberInfoEmailMailPath(String email);
}
