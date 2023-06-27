package com.ticketing.domain.venue.service;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.service.AdminService;
import com.ticketing.domain.venue.dto.request.VenueCreateReq;
import com.ticketing.domain.venue.dto.response.VenueCreateRes;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.exception.NotFoundVenueException;
import com.ticketing.domain.venue.repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VenueService {

  private final VenueRepository venueRepository;
  private final AdminService adminService;

  public VenueService(VenueRepository venueRepository, AdminService adminService) {
    this.venueRepository = venueRepository;
    this.adminService = adminService;
  }

  @Transactional
  public VenueCreateRes creat(VenueCreateReq venueCreateReq, Long venueManagerId) {
    Admin admin = adminService.getById(venueManagerId);
    Venue savedVenue = venueRepository.save(venueCreateReq.toEntity(admin));

    return VenueCreateRes.from(savedVenue);
  }

  public Venue getById(Long id) {
    return venueRepository.findById(id)
        .orElseThrow(NotFoundVenueException::new);
  }

}
