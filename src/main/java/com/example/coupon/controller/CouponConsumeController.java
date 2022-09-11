package com.example.coupon.controller;

import com.example.coupon.dto.CouponValidationRequest;
import com.example.coupon.dto.ValidationResponse;
import com.example.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/coupons")
public class CouponConsumeController {

  @Autowired private CouponService couponService;

  @PostMapping("/validations/redemption/{code}")
  public ValidationResponse validateIfCouponIsValidToRedeem(
      @RequestBody @Valid CouponValidationRequest request, @PathVariable String code) {
    couponService.throwExceptionIfCouponNotValid(request, code);

    return new ValidationResponse(true);
  }

  @PostMapping("/redemption/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void redemption(@PathVariable String code) {
    couponService.redeemCoupon(code);
  }

  @PostMapping("/refund/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void refund(@PathVariable String code) {
    couponService.refundCoupon(code);
  }
}
