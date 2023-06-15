package com.ticketing.domain.venue.service;

import com.ticketing.domain.venue.dto.VenueCreateReq;
import com.ticketing.domain.venue.dto.VenueCreateRes;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VenueService {

  private final VenueRepository venueRepository;

  public VenueService(VenueRepository venueRepository) {
    this.venueRepository = venueRepository;
  }

  @Transactional
  public VenueCreateRes creat(VenueCreateReq venueCreateReq) {
    Venue savedVenue = venueRepository.save(venueCreateReq.toEntity());
    return VenueCreateRes.from(savedVenue);
  }

  public Venue getBy(Long id) {
    return venueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
