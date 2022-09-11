package com.example.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class CouponValidationRequest {
  @Min(value = 0, message = "amount must not be negative")
  private Long amount;
}
