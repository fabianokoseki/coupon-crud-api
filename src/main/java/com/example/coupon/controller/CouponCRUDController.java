package com.example.coupon.controller;

import com.example.coupon.dto.CouponDTO;
import com.example.coupon.entity.Coupon;
import com.example.coupon.exception.BusinessException;
import com.example.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponCRUDController {

  @Autowired private CouponService couponService;

  @GetMapping
  public List<Coupon> getList(
      @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    return couponService.getCoupons(page, size);
  }

  @GetMapping("/{id}")
  public Coupon getCoupon(@PathVariable Long id) throws BusinessException {
    return couponService.getCoupon(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteCoupon(@PathVariable Long id) throws BusinessException {
    couponService.deleteCoupon(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Coupon create(@RequestBody @Valid CouponDTO couponDTO) {
    return couponService.create(couponDTO);
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Coupon update(@RequestBody @Valid CouponDTO couponDTO) {
    return couponService.update(couponDTO);
  }
}
