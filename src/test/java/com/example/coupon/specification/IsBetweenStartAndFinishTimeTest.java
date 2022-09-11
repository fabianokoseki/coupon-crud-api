package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsBetweenStartAndFinishTimeTest {

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

  @Test
  public void testIsBetweenStartAndFinishTimeSuccess() {
    final var spec = new IsBetweenStartAndFinishTime();
    final var result = spec.isSatisfiedBy(createCoupon(null, null));

    assertFalse(result.hasErrors());
  }

  @Test
  public void testIsBetweenStartAndFinishTimeFailBecauseCouponIsExpired() {
    final var spec = new IsBetweenStartAndFinishTime();
    final var result =
        spec.isSatisfiedBy(
            createCoupon(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1)));

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(spec.getErrorMessageExpired()), result.getErrors());
  }

  @Test
  public void testIsBetweenStartAndFinishTimeFailBecauseCouponDidNotStart() {
    final var spec = new IsBetweenStartAndFinishTime();
    final var result =
        spec.isSatisfiedBy(
            createCoupon(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)));

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(spec.getErrorMessageStart()), result.getErrors());
  }
}
