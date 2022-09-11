package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor
public class IsBetweenStartAndFinishTime implements Specification<CouponDTO> {

  @Getter private final String errorMessageStart = "Coupon has not started yet";
  @Getter private final String errorMessageExpired = "Coupon is expired";
  private SpecificationResult result = new SpecificationResult();

  IsBetweenStartAndFinishTime(SpecificationResult result) {
    this.result = result;
  }

  @Override
  public SpecificationResult isSatisfiedBy(CouponDTO coupon) {
    if (coupon.getStartAt().isAfter(LocalDateTime.now(ZoneOffset.UTC)))
      result.addError(errorMessageStart);

    if (coupon.getFinishAt().isBefore(LocalDateTime.now())) result.addError(errorMessageExpired);

    return result;
  }
}
