package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponWithCodeDoesNotExistsTest {

  private static final String COUPON_NAME = "COUPONTEST";

  private CouponDTO createCoupon() {
    return new CouponDTO(
        1L,
        "Coupon Test",
        COUPON_NAME,
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
  public void couponWithCodeDoesNotExistsFail() {
    final var spec = new CouponWithCodeDoesNotExists(COUPON_NAME);

    final var result = spec.isSatisfiedBy(createCoupon());

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(spec.getErrorMessage()), result.getErrors());
  }

  @Test
  public void couponWithCodeDoesNotExistsSuccess() {
    final var spec = new CouponWithCodeDoesNotExists("NONEXISTENTCODE");

    final var result = spec.isSatisfiedBy(createCoupon());

    assertFalse(result.hasErrors());
  }
}
