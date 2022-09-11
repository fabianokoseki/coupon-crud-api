package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HasEnoughAmountForMinAmountTest {
  private CouponDTO createCoupon(Long minAmount) {
    return new CouponDTO(
        1L,
        "Coupon Test",
        "COUPONTEST",
        "A coupon test",
        500,
        10,
        50L,
        minAmount != null ? minAmount : 150,
        "FIXED",
        "ACTIVE",
        LocalDateTime.now().minusDays(1),
        LocalDateTime.now().plusDays(1),
        LocalDate.now(),
        LocalDate.now());
  }

  @Test
  public void testHasEnoughAmountForMinAmountFail() {
    final var spec = new HasEnoughAmountForMinAmount(150L);

    final var result = spec.isSatisfiedBy(createCoupon(200L));

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(spec.getErrorMessage()), result.getErrors());
  }

  @Test
  public void testHasEnoughAmountForMinAmountSuccess() {
    final var spec = new HasEnoughAmountForMinAmount(150L);

    final var result = spec.isSatisfiedBy(createCoupon(200L));

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(spec.getErrorMessage()), result.getErrors());
  }
}
