package com.example.coupon.dao;

import com.example.coupon.exception.DAOException;
import com.example.coupon.exception.DAOExceptions;
import com.example.coupon.repository.CouponRepository;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponDaoTest {

  @Mock private CouponRepository couponRepository;

  private CouponDao couponDao;

  @BeforeAll
  public void setUp() {
    couponRepository = mock(CouponRepository.class);
    couponDao = new CouponDao(couponRepository);
  }

  @Test
  public void testRedeemWillThrowDAOExceptionWithSpecificMessage() {
    String couponSoldOut = "COUPONSOLDOUT";
    final var daoException = DAOExceptions.UNSIGNED_VALUE_OUT_RANGE.get();

    final var sqlException = new SQLException("could not execute statement", "22001", 1690);
    final var dataException = new DataException("could not execute statement", sqlException);

    doThrow(
            new DataIntegrityViolationException(
                "could not execute statement; SQL [n/a]", dataException))
        .when(couponRepository)
        .redeem(couponSoldOut);

    doThrow(DAOExceptions.UNSIGNED_VALUE_OUT_RANGE.get())
        .when(couponRepository)
        .redeem(couponSoldOut);

    final var exception =
        assertThrows(
            DAOException.class, () -> couponDao.redeem(couponSoldOut), daoException.getMessage());

    assertEquals(daoException.getMessage(), exception.getMessage());
  }
}
