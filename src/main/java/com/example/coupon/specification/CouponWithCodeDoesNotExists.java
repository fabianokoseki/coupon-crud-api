package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import lombok.Getter;

public class CouponWithCodeDoesNotExists implements Specification<CouponDTO> {

  @Getter private final String errorMessage = "Coupon with code already exists";
  private final String couponCode;
  private SpecificationResult result = new SpecificationResult();

  public CouponWithCodeDoesNotExists(String couponCode) {
    this.couponCode = couponCode;
  }

  public CouponWithCodeDoesNotExists(String couponCode, SpecificationResult result) {
    this.couponCode = couponCode;
    this.result = result;
  }

  @Override
  public SpecificationResult isSatisfiedBy(CouponDTO coupon) {
    if (couponCode.equals(coupon.getCode())) result.addError(this.errorMessage);

    return result;
  }
}
