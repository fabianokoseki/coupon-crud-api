package com.example.coupon.dao;

import com.example.coupon.entity.Coupon;
import com.example.coupon.enums.MySQLErrorCodes;
import com.example.coupon.exception.DAOException;
import com.example.coupon.exception.DAOExceptions;
import com.example.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponDao {

  private final CouponRepository couponRepository;

  public Page<Coupon> findAll(PageRequest pageRequest) {
    return couponRepository.findAll(pageRequest);
  }

  public Optional<Coupon> findById(Long id) {
    return couponRepository.findById(id);
  }

  public Coupon save(Coupon coupon) {
    return couponRepository.save(coupon);
  }

  public Coupon findByCode(String code) {
    return couponRepository.findByCode(code);
  }

  public void redeem(String code) {
    try {
      couponRepository.redeem(code);
    } catch (DataIntegrityViolationException e) {
      Optional.of(e)
          .map(error -> (DataException) error.getCause())
          .filter(
              error ->
                  error.getErrorCode() == MySQLErrorCodes.UNSIGNED_VALUE_OUT_RANGE.getErrorCode())
          .ifPresent(
              error -> {
                throw DAOExceptions.UNSIGNED_VALUE_OUT_RANGE.get();
              });

      throw new DAOException();
    }
  }

  public void refund(String code) {
    couponRepository.refund(code);
  }
}
