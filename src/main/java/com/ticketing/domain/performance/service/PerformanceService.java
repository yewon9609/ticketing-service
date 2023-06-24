package com.ticketing.domain.performance.service;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.service.AdminService;
import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceCreateRes;
import com.ticketing.domain.performance.dto.response.PerformanceDetailRes;
import com.ticketing.domain.performance.dto.response.PerformanceRemainingSeatsRes;
import com.ticketing.domain.performance.dto.response.PerformanceSimpleRes;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.exception.NotFoundPerformanceException;
import com.ticketing.domain.performance.repository.PerformanceRepository;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.service.VenueService;
import com.ticketing.global.exception.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PerformanceService {

  private final PerformanceRepository performanceRepository;
  private final VenueService venueService;
  private final AdminService adminService;

  public PerformanceService(PerformanceRepository performanceRepository, VenueService venueService,
      AdminService adminService) {
    this.performanceRepository = performanceRepository;
    this.venueService = venueService;
    this.adminService = adminService;
  }

  @Transactional
  public PerformanceCreateRes create(PerformanceCreateReq createReq, Long adminId) {
    Venue venue = venueService.getBy(createReq.venueId());
    Admin admin = adminService.getBy(adminId);
    Performance performance = performanceRepository.save(createReq.toEntity(venue, admin));

    return PerformanceCreateRes.from(performance);
  }

  public Performance get(Long performanceId) {
    return performanceRepository.findById(performanceId)
        .orElseThrow(() -> new NotFoundPerformanceException(ErrorCode.NOT_FOUND_PERFORMANCE));
  }

  @Transactional
  public PerformanceDetailRes getDetails(Long id) {
    return PerformanceDetailRes.from(get(id));
  }

  public PerformanceRemainingSeatsRes getRemainingSeatsCount(Long id) {
    return new PerformanceRemainingSeatsRes(get(id).getSeat()
        .getCapacity());
  }

  public Slice<PerformanceSimpleRes> getList(Pageable pageable) {
    return performanceRepository.getList(pageable);
  }

}
