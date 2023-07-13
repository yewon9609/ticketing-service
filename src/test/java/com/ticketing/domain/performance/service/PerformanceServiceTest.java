package com.ticketing.domain.performance.service;

import static com.ticketing.testFixture.TestFixture.createPerformance;
import static com.ticketing.testFixture.TestFixture.createPerformanceCreateReq;
import static com.ticketing.testFixture.TestFixture.createPerformanceManagerWithId;
import static com.ticketing.testFixture.TestFixture.createPerformanceSimpleRes;
import static com.ticketing.testFixture.TestFixture.createPerformanceWithId;
import static com.ticketing.testFixture.TestFixture.createVenueWithId;
import static com.ticketing.testFixture.TestFixture.createVenueManagerWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.admin.service.AdminService;
import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceCreateRes;
import com.ticketing.domain.performance.dto.response.PerformanceDetailRes;
import com.ticketing.domain.performance.dto.response.PerformanceSimpleRes;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.repository.PerformanceRepository;
import com.ticketing.domain.venue.entity.Venue;
import com.ticketing.domain.venue.service.VenueService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Performance Service 클래스")
class PerformanceServiceTest {

  @Mock
  private PerformanceRepository performanceRepository;
  @Mock
  private VenueService venueService;
  @Mock
  private AdminService adminService;
  @InjectMocks
  private PerformanceService performanceService;

  private Venue venue;
  private Performance performance;
  private Admin venueManager;
  private Admin performanceManager;

  @BeforeEach
  void setUp() {
    venueManager = createVenueManagerWithId();
    performanceManager = createPerformanceManagerWithId();
    venue = createVenueWithId(venueManager);
  }

  @DisplayName("공연 정보를 생성할 수 있다")
  @Test
  void test_create() {
    PerformanceCreateReq createReq = createPerformanceCreateReq(venue.getId());
    Performance expected = createPerformance(createReq, venue, performanceManager);

    when(venueService.getById(any())).thenReturn(venue);
    when(adminService.getById(any())).thenReturn(performanceManager);
    when(performanceRepository.save(any())).thenReturn(expected);

    PerformanceCreateRes savedPerformance = performanceService.create(createReq,
        performanceManager.getId());

    assertThat(savedPerformance.id()).isEqualTo(expected.getId());
  }

  @DisplayName("공연 정보를 상세조회 할 수 있다")
  @Test
  void test_get_details() {
    Performance expectedDetails = createPerformance(performanceManager, venue);
    when(performanceRepository.findById(any())).thenReturn(Optional.of(expectedDetails));
    PerformanceDetailRes details = performanceService.getDetails(expectedDetails.getId());

    assertThat(details.title()).isEqualTo(expectedDetails.getTitle());
  }

  @DisplayName("공연 정보를 리스트로 조회할 수 있다")
  @Test
  void test_get_list() {
    List<PerformanceSimpleRes> performanceSimpleResList = List.of(
        createPerformanceSimpleRes(),
        createPerformanceSimpleRes(),
        createPerformanceSimpleRes()
    );

    PageRequest pageRequest = PageRequest.of(0, 2);

    Slice<PerformanceSimpleRes> expectedList = new SliceImpl<>(
        performanceSimpleResList,
        pageRequest,
        pageRequest.next().isPaged()
    );

    when(performanceRepository.getList(any())).thenReturn(expectedList);
    Slice<PerformanceSimpleRes> performanceList = performanceService.getList(pageRequest);

    assertThat(performanceList).isEqualTo(expectedList);
  }

  @DisplayName("공연 예약시 잔여 좌석을 차감할 수 있다")
  @Test
  void test_subtract_seats() {
    performance = createPerformanceWithId(performanceManager, venue);
    int customerAge = performance.getViewingAge() + 1;
    int ticketCount = 1;
    int expectedResult = performance.getSeatCapacity() - ticketCount;

    when(performanceRepository.findById(any())).thenReturn(Optional.ofNullable(performance));
    Performance afterPerformance = performanceService.subtractSeats(
        performance.getId(),
        customerAge,
        ticketCount
    );

    assertThat(afterPerformance.getSeatCapacity()).isEqualTo(expectedResult);
  }




}