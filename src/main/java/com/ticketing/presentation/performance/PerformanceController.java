package com.ticketing.presentation.performance;

import com.ticketing.domain.performance.dto.response.PerformanceDetailRes;
import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceCreateRes;
import com.ticketing.domain.performance.service.PerformanceService;
import com.ticketing.global.config.jwt.CustomUserDetails.AdminInfo;
import jakarta.validation.Valid;
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
    PerformanceDetailRes detail = performanceService.getDetail(performanceId);
    return ResponseEntity.ok(detail);
  }

}
