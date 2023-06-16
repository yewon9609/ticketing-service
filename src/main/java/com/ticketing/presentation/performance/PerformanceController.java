package com.ticketing.presentation.performance;

import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceCreateRes;
import com.ticketing.domain.performance.service.PerformanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/{adminId}")
  public ResponseEntity<PerformanceCreateRes> create(
      @RequestBody @Valid PerformanceCreateReq createReq,
      @PathVariable Long adminId
  ) {
    PerformanceCreateRes createRes = performanceService.create(createReq, adminId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createRes);
  }

}
