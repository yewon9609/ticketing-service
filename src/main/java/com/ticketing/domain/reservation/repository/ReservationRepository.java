package com.ticketing.domain.reservation.repository;

import com.ticketing.domain.reservation.dto.response.ReservationDetailRes;
import com.ticketing.domain.reservation.dto.response.ReservationSimpleRes;
import com.ticketing.domain.reservation.entity.Reservation;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  @Query("select new com.ticketing.domain.reservation.dto.response.ReservationDetailRes("
      + "c.memberInfo.name, r.createDate, r.ticketCount, r.totalCost, r.performance.title, "
      + "r.performance.schedule.startDate, r.performance.schedule.startTime, "
      + "r.performance.venue.name, r.performance.venue.address) "
      + "from Reservation r "
      + "left join Customer c on c.id = r.customer.id "
      + "where r.customer.id = :customerId and r.id = :id")
  Optional<ReservationDetailRes> getDetail(@Param("id") Long id,
      @Param("customerId") Long customerId);

  @Query(
      "select new com.ticketing.domain.reservation.dto.response.ReservationSimpleRes("
          + " r.createDate, r.performance.thumbnail, r.performance.title, r.totalCost, r.ticketCount, r.performance.schedule.startDate, r.performance.schedule.startTime )"
          + "from Reservation r "
          + "where r.customer.id = :customerId "
          + "order by r.createDate"
  )
  Slice<ReservationSimpleRes> getList(@Param("customerId") Long customerId, Pageable pageable);
}
