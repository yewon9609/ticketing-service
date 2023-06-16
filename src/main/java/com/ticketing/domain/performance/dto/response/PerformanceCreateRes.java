package com.ticketing.domain.performance.dto.response;

import com.ticketing.domain.performance.entity.Performance;

public record PerformanceCreateRes(

  Long id

) {

  public static PerformanceCreateRes from(Performance performance) {
    return new PerformanceCreateRes(performance.getId());
  }

}
