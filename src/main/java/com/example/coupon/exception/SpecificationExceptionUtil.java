package com.example.coupon.exception;

import com.example.coupon.specification.SpecificationResult;

import java.util.Map;
import java.util.stream.Collectors;

public class SpecificationExceptionUtil {
  public static void throwBusinessException(SpecificationResult result) {
    var details =
        result.getErrors().stream()
            .map(error -> Map.of("reason", error))
            .collect(Collectors.toList());

    throw BusinessExceptions.INVALID_PARAMS.get().details(details);
  }
}
