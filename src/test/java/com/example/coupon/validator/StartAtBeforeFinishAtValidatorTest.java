package com.example.coupon.validator;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StartAtBeforeFinishAtValidatorTest {

  @Test
  public void testStartAtBeforeFinishAtIsValid() {
    final var validator = new StartAtBeforeFinishAtValidator();
    assertTrue(validator.isValid(createCoupon(null, null), null));
  }

  @Test
  public void testStartAtBeforeFinishAtIsInvalid() {
    final var validator = new StartAtBeforeFinishAtValidator();
    assertFalse(
        validator.isValid(
            createCoupon(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(3)),
            null));
  }

  private CouponDTO createCoupon(LocalDateTime startAt, LocalDateTime finishAt) {
    return new CouponDTO(
        1L,
        "Coupon Test",
        "COUPONTEST",
        "A coupon test",
        10,
        10,
        50L,
        150L,
        "FIXED",
        "ACTIVE",
        startAt != null ? startAt : LocalDateTime.now().minusDays(1),
        finishAt != null ? finishAt : LocalDateTime.now().plusDays(1),
        LocalDate.now(),
        LocalDate.now());
  }
}
