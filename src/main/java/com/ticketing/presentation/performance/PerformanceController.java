package com.ticketing.presentation.performance;

import com.ticketing.domain.performance.dto.response.PerformanceDetailRes;
import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceCreateRes;
import com.ticketing.domain.performance.dto.response.PerformanceRemainingSeatsRes;
import com.ticketing.domain.performance.dto.response.PerformanceSimpleRes;
import com.ticketing.domain.performance.service.PerformanceService;
import com.ticketing.global.config.jwt.CustomUserDetails.AdminInfo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

  private static final int DEFAULT_SIZE = 5;

  private final PerformanceService performanceService;

  public PerformanceController(PerformanceService performanceService) {
    this.performanceService = performanceService;
  }

  @PostMapping
  public ResponseEntity<PerformanceCreateRes> create(
      @RequestBody @Valid PerformanceCreateReq createReq,
      @AuthenticationPrincipal AdminInfo adminInfo
  ) {
    PerformanceCreateRes createRes = performanceService.create(createReq, adminInfo.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(createRes);
  }

  @GetMapping("/{performanceId}")
  public ResponseEntity<PerformanceDetailRes> get(@PathVariable Long performanceId) {
    PerformanceDetailRes performance = performanceService.getDetails(performanceId);
    return ResponseEntity.ok(performance);
  }

  @GetMapping
  public ResponseEntity<Slice<PerformanceSimpleRes>> getAll(
      @PageableDefault(size = DEFAULT_SIZE) Pageable pageable
  ) {
    Slice<PerformanceSimpleRes> performances = performanceService.getList(pageable);
    return ResponseEntity.ok(performances);
  }

  @GetMapping("/{performanceId}/seats")
  public ResponseEntity<PerformanceRemainingSeatsRes> getSeatCount(
      @PathVariable Long performanceId
  ) {
    PerformanceRemainingSeatsRes seatCount =
        performanceService.getRemainingSeatsCount(performanceId);

    return ResponseEntity.ok(seatCount);
  }

}
