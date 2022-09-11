package com.example.coupon.service;

import com.example.coupon.bean.ConfigBean;
import com.example.coupon.dao.CouponDao;
import com.example.coupon.dto.CouponValidationRequest;
import com.example.coupon.entity.Coupon;
import com.example.coupon.enums.CouponStatusEnum;
import com.example.coupon.enums.DiscountTypeEnum;
import com.example.coupon.exception.BusinessException;
import com.example.coupon.exception.BusinessExceptions;
import com.example.coupon.exception.DAOExceptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponServiceTest {
  @Mock private CouponDao couponDao;
  private CouponService couponService;

  @BeforeAll
  public void setUp() {
    couponDao = mock(CouponDao.class);
    couponService = new CouponService(couponDao, new ConfigBean().modelMapper());
  }

  @Test
  public void testFetchCoupons() {
    final var output = new ArrayList<Coupon>();
    output.add(new Coupon());

    final var page = 0;
    final var size = 10;

    final var pageReturn = new PageImpl<>(output);
    final var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

    when(couponDao.findAll(pageable)).thenReturn(pageReturn);
    final var result = couponService.getCoupons(page, size);
    assertFalse(result.isEmpty());
  }

  @Test
  public void testGetCoupon() {
    final var coupon = new Coupon();
    coupon.setId(1L);

    when(couponDao.findById(1L)).thenReturn(Optional.of(coupon));
    final var result = couponService.getCoupon(1L);

    assertNotNull(result);
  }

  @Test
  public void testGetCouponException() {
    final var businessException = BusinessExceptions.COUPON_NOT_FOUND.get();

    when(couponDao.findById(999L)).thenReturn(Optional.empty());

    exceptionIsThrown(() -> couponService.getCoupon(999L), businessException);
  }

  @Test()
  public void testRedeemCouponWillNotThrowException() {
    var couponCode = "VALIDCODE";
    when(couponDao.findByCode(couponCode)).thenReturn(createCoupon());
    assertDoesNotThrow(() -> couponService.redeemCoupon(couponCode));
  }

  @Test()
  public void testRedeemCouponWillThrowBusinessExceptionBecauseCouponWithCodeWasNotFound() {
    var couponCode = "NONEXISTANTCODE";
    when(couponDao.findByCode(couponCode)).thenReturn(null);

    final var businessException = BusinessExceptions.INVALID_PARAMS.get();
    exceptionIsThrown(() -> couponService.redeemCoupon(couponCode), businessException);
  }

  @Test()
  public void testRedeemCouponWillThrowCouponSoldOutException() {
    String couponSoldOut = "COUPONSOLDOUT";
    final var businessException = BusinessExceptions.COUPON_SOLD_OUT.get();

    when(couponDao.findByCode(couponSoldOut)).thenReturn(createCoupon());

    doThrow(DAOExceptions.UNSIGNED_VALUE_OUT_RANGE.get()).when(couponDao).redeem(couponSoldOut);

    exceptionIsThrown(() -> couponService.redeemCoupon(couponSoldOut), businessException);
  }

  @Test
  public void testThrowExceptionBecauseCouponIsInvalid() {
    final var invalidParamsException = BusinessExceptions.INVALID_PARAMS.get();

    final var invalidCode = "INVALIDCODE";
    when(couponDao.findByCode(invalidCode)).thenReturn(createCoupon());

    exceptionIsThrown(
        () ->
            couponService.throwExceptionIfCouponNotValid(
                new CouponValidationRequest(100L), invalidCode),
        invalidParamsException);
  }

  @Test
  public void testExceptionIsNotThrownBecauseCouponIsValid() {
    final var validCode = "VALIDCODE";
    when(couponDao.findByCode(validCode)).thenReturn(createCoupon());

    assertDoesNotThrow(
        () ->
            couponService.throwExceptionIfCouponNotValid(
                new CouponValidationRequest(200L), validCode));
  }

  @Test
  public void testDeleteCouponSuccess() {
    final var couponId = 1L;
    Coupon couponInput = createCoupon();
    when(couponDao.findById(couponId)).thenReturn(Optional.of(couponInput));

    final var couponCaptor = ArgumentCaptor.forClass(Coupon.class);

    couponService.deleteCoupon(couponId);

    verify(couponDao, times(1)).save(couponCaptor.capture());

    final var capturedCoupon = couponCaptor.getValue();

    assertEquals(CouponStatusEnum.REMOVED, capturedCoupon.getStatus());
  }

  private Coupon createCoupon() {
    return new Coupon(
        1L,
        "Coupon Test",
        "VALIDCODE",
        50L,
        150L,
        LocalDateTime.now().minusDays(1),
        LocalDateTime.now().plusDays(1),
        "A coupon test",
        10,
        10,
        CouponStatusEnum.ACTIVE,
        DiscountTypeEnum.FIXED);
  }

  private void exceptionIsThrown(Executable ex, BusinessException businessException) {
    final var exception = assertThrows(BusinessException.class, ex, businessException.message());

    assertEquals(businessException.code(), exception.code());
    assertEquals(businessException.message(), exception.message());
    assertEquals(businessException.details(), exception.details());
  }
}
