package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import lombok.Getter;

public class HasEnoughAmountForMinAmount implements Specification<CouponDTO> {

  @Getter
  private final String errorMessage =
      "The given amount is not sufficient for the coupon " + "minimum amount.";

  private SpecificationResult result = new SpecificationResult();

  private Long inputAmount;

  public HasEnoughAmountForMinAmount(SpecificationResult result) {
    this.result = result;
  }

  public HasEnoughAmountForMinAmount(Long inputAmount) {
    this.inputAmount = inputAmount;
  }

  @Override
  public SpecificationResult isSatisfiedBy(CouponDTO coupon) {
    if (inputAmount < coupon.getMinimumAmountToApply()) result.addError(errorMessage);

    return result;
  }
}
