package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;

import java.util.List;

public class CouponSpecificationFactory {

  public static AndSpecification<CouponDTO> constructForCreateValidation(String code) {
    return new AndSpecification<>(List.of(new CouponWithCodeDoesNotExists(code)));
  }

  public static AndSpecification<CouponDTO> constructForConsumeValidation(Long inputAmount) {
    return new AndSpecification<>(
        List.of(
            new CouponExists(),
            new IsActive(),
            new IsBetweenStartAndFinishTime(),
            new HasEnoughQuantity(),
            new HasEnoughAmountForMinAmount(inputAmount)));
  }

  public static AndSpecification<CouponDTO> constructForRedeemValidation() {
    return new AndSpecification<>(
        List.of(
            new CouponExists(),
            new IsActive(),
            new IsBetweenStartAndFinishTime(),
            new HasEnoughQuantity()));
  }

}
