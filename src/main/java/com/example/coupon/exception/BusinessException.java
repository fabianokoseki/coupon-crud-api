package com.example.coupon.exception;

import lombok.*;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class BusinessException extends RuntimeException {
  @NonNull @Getter private String code;

  @NonNull @Getter private String message;

  @Getter private final Integer httpStatusCode = 400;

  @Getter @Setter private Object details;
}
