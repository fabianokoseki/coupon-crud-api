package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponSpecificationFactoryTest {

  private CouponDTO create(Long minAmount) {
    return new CouponDTO(
        1L,
        "Coupon Test",
        "COUPONTEST",
        "A coupon test",
        500,
        10,
        50L,
        minAmount != null ? minAmount : 150L,
        "FIXED",
        "ACTIVE",
        LocalDateTime.now().minusDays(1),
        LocalDateTime.now().plusDays(1),
        LocalDate.now(),
        LocalDate.now());
  }

  @Test
  public void testForConsumeValidationFail() {
    final var spec = CouponSpecificationFactory.constructForConsumeValidation(200L);

    final var result = spec.isSatisfiedBy(create(null));

    assertFalse(result.isPresent());
  }

  @Test
  public void testConsumeValidationSuccess() {
    final var spec = CouponSpecificationFactory.constructForConsumeValidation(400L);

    final var result = spec.isSatisfiedBy(create(401L));

    assertTrue(result.isPresent());
  }
}
