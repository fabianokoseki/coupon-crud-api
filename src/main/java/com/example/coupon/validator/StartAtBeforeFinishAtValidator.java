package com.example.coupon.validator;

import com.example.coupon.dto.CouponDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartAtBeforeFinishAtValidator
    implements ConstraintValidator<StartAtBeforeFinishAt, CouponDTO> {

  @Override
  public boolean isValid(CouponDTO coupon, ConstraintValidatorContext context) {
    return coupon.getStartAt().isBefore(coupon.getFinishAt());
  }
}
