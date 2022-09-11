package com.example.coupon.repository;

import com.example.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
  Coupon findByCode(String code);

  @Modifying
  @Query(
      value = "UPDATE coupon SET quantity = quantity - 1, usages = usages + 1 WHERE code = :code",
      nativeQuery = true)
  void redeem(String code);

  @Modifying
  @Query(
      value = "UPDATE coupon SET quantity = quantity + 1, usages = usages - 1 WHERE code = :code",
      nativeQuery = true)
  void refund(String code);
}
