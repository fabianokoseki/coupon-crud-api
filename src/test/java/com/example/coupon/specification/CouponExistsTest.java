package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponExistsTest {
  private CouponDTO createCoupon() {
    return new CouponDTO(
        1L,
        "Coupon Test",
        "COUPONTEST",
        "A coupon test",
        500,
        10,
        50L,
        150L,
        "FIXED",
        "ACTIVE",
        LocalDateTime.now().minusDays(1),
        LocalDateTime.now().plusDays(1),
        LocalDate.now(),
        LocalDate.now());
  }

  @Test
  public void testCouponExistFail() {
    final var couponExistsSpec = new CouponExists();

    final var result = couponExistsSpec.isSatisfiedBy(null);

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(couponExistsSpec.getErrorMessage()), result.getErrors());
  }

  @Test
  public void testSuccessCouponExistSuccess() {
    final var couponExistsSpec = new CouponExists();

    final var result = couponExistsSpec.isSatisfiedBy(createCoupon());

    assertFalse(result.hasErrors());
  }
}
