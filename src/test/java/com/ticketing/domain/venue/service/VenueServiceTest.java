package com.ticketing.domain.venue.service;

import static com.ticketing.testFixture.TestFixture.createVenueWithId;
import static com.ticketing.testFixture.TestFixture.createVenueManagerWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.service.AdminService;
import com.ticketing.domain.venue.dto.request.VenueCreateReq;
import com.ticketing.domain.venue.dto.response.VenueCreateRes;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.repository.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@DisplayName("Venue Service 클래스")
class VenueServiceTest {

  @Mock
  private VenueRepository venueRepository;
  @Mock
  private AdminService adminService;
  @InjectMocks
  private VenueService venueService;

  private Admin venueManager;

  @BeforeEach
  void setUp() {
    venueManager = createVenueManagerWithId();
  }

  @DisplayName("공연장을 생성할 수 있다")
  @Test
  void test_create() {
    Venue venue = createVenueWithId(venueManager);
    VenueCreateRes expectedVenue = VenueCreateRes.from(venue);
    VenueCreateReq venueCreateReq = new VenueCreateReq(
        venue.getName(),
        venue.getAddress(),
        venue.getPhoneNumber(),
        venue.getTotalSeats()
    );

    when(venueRepository.save(any())).thenReturn(venue);
    VenueCreateRes savedVenue = venueService.creat(venueCreateReq, venueManager.getId());
    assertThat(savedVenue).isEqualTo(expectedVenue);
  }

}