package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import com.example.coupon.enums.CouponStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IsActive implements Specification<CouponDTO> {

  @Getter private final String errorMessage = "The coupon is not active";
  private SpecificationResult result = new SpecificationResult();

  public IsActive(SpecificationResult result) {
    this.result = result;
  }

  @Override
  public SpecificationResult isSatisfiedBy(CouponDTO couponDTO) {
    if (!couponDTO.getStatus().equals(CouponStatusEnum.ACTIVE.toString()))
      result.addError(this.errorMessage);

    return result;
  }
}
