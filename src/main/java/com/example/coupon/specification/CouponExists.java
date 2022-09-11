package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CouponExists implements Specification<CouponDTO> {

  @Getter private final String errorMessage = "Coupon not found";
  private SpecificationResult result = new SpecificationResult();

  public CouponExists(SpecificationResult result) {
    this.result = result;
  }

  @Override
  public SpecificationResult isSatisfiedBy(CouponDTO element) {
    if (element == null) result.addError(errorMessage);

    return result;
  }
}
