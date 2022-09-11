package com.example.coupon.specification;

import com.example.coupon.dto.CouponDTO;
import com.example.coupon.enums.CouponStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsActiveTest {

  private CouponDTO createCoupon(String isActive) {
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
        isActive != null ? isActive : CouponStatusEnum.ACTIVE.toString(),
        LocalDateTime.now().minusDays(1),
        LocalDateTime.now().plusDays(1),
        LocalDate.now(),
        LocalDate.now());
  }

  @Test
  public void testIsActiveFail() {
    final var spec = new IsActive();
    final var result = spec.isSatisfiedBy(this.createCoupon(CouponStatusEnum.INACTIVE.toString()));

    assertTrue(result.hasErrors());
    assertIterableEquals(List.of(spec.getErrorMessage()), result.getErrors());
  }

  @Test
  public void testIsActiveSuccess() {
    final var spec = new IsActive();
    final var result = spec.isSatisfiedBy(this.createCoupon(null));

    assertFalse(result.hasErrors());
  }
}
