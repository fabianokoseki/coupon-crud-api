package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HasEnoughQuantity implements Specification<CouponDTO> {

  @Getter private final String errorMessage = "Coupon does not have enough quantity";

  private SpecificationResult result = new SpecificationResult();

  public HasEnoughQuantity(SpecificationResult result) {
    this.result = result;
  }

  @Override
  public SpecificationResult isSatisfiedBy(CouponDTO element) {
    if (element.getQuantity() < 1) result.addError("Coupon does not have enough quantity");

    return result;
  }
}
