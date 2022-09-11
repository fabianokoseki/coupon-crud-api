package com.example.coupon.exception;

public enum BusinessExceptions {
  GENERIC_ERROR(new BusinessException("coupon-0000", "Internal Server Error")),
  INVALID_PARAMS(new BusinessException("coupon-0001", "Invalid params")),
  COUPON_NOT_FOUND(new BusinessException("coupon-0002", "Coupon not found")),
  COUPON_SOLD_OUT(new BusinessException("coupon-0003", "Coupon has sold out"));

  private final BusinessException exception;

  BusinessExceptions(BusinessException e) {
    this.exception = e;
  }

  public BusinessException get() {
    return this.exception;
  }
}
