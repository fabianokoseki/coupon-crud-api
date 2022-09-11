package com.example.coupon.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonSerialize
public class ValidationResponse {
  @Getter private final boolean isValid;
}
