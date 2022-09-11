package com.example.coupon.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
public class DAOException extends RuntimeException {

  @NonNull @Getter private String message;
}
