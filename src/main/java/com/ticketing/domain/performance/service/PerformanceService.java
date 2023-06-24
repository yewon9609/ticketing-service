package com.ticketing.domain.performance.service;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.service.AdminService;
import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceCreateRes;
import com.ticketing.domain.performance.dto.response.PerformanceDetailRes;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.repository.PerformanceRepository;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.service.VenueService;
import jakarta.persistence.EntityNotFoundException;
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

  public Performance getBy(Long performanceId) {
    return performanceRepository.findById(performanceId)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public PerformanceDetailRes getDetail(Long performanceId) {
    return PerformanceDetailRes.from(getBy(performanceId));
  }

}
