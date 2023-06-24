package com.ticketing.domain.performance.repository;

import com.ticketing.domain.performance.dto.response.PerformanceSimpleRes;
import com.ticketing.domain.performance.entity.Performance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

  @Query("select new com.ticketing.domain.performance.dto.response.PerformanceSimpleRes(p.title, p.thumbnail, p.schedule.startDate, p.schedule.startTime, p.seat.capacity, p.venue.name) "
      + "from Performance p "
      + "order by p.createDate")
  Slice<PerformanceSimpleRes> getList(Pageable pageable);
}
