package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HasEnoughQuantityTest {

  private CouponDTO createCoupon(Integer quantity) {
    return new CouponDTO(
        1L,
        "Coupon Test",
        "COUPONTEST",
        "A coupon test",
        quantity != null ? quantity : 10,
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
  public void testHasEnoughQuantityFail() {
    final var spec = new HasEnoughQuantity();
    final var result = spec.isSatisfiedBy(createCoupon(0));

    assertTrue(result.hasErrors());
    assertEquals(List.of(spec.getErrorMessage()), result.getErrors());
  }

  @Test
  public void testHasEnoughQuantitySuccess() {
    final var spec = new HasEnoughQuantity();
    final var result = spec.isSatisfiedBy(createCoupon(10));

    assertFalse(result.hasErrors());
  }
}
