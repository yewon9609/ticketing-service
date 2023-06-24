package com.ticketing.domain.member.admin.repository;

import com.ticketing.domain.member.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

  @Query("select a "
      + "from Admin a "
      + "where a.memberInfo.email.mailPath = :email")
  Optional<Admin> findByEmail(@Param("email") String email);

}
