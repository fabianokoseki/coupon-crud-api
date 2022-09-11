package com.example.coupon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MySQLErrorCodes {
  UNSIGNED_VALUE_OUT_RANGE(1690);

  @Getter private final Integer errorCode;
}
