package com.example.coupon.service;

import com.example.coupon.dao.CouponDao;
import com.example.coupon.dto.CouponDTO;
import com.example.coupon.dto.CouponValidationRequest;
import com.example.coupon.entity.Coupon;
import com.example.coupon.enums.CouponStatusEnum;
import com.example.coupon.exception.BusinessExceptions;
import com.example.coupon.exception.DAOException;
import com.example.coupon.exception.DAOExceptions;
import com.example.coupon.exception.SpecificationExceptionUtil;
import com.example.coupon.specification.CouponSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CouponService {

  private final CouponDao couponDAO;
  private final ModelMapper modelMapper;

  Logger logger = Logger.getLogger(CouponService.class.getName());

  public List<Coupon> getCoupons(Integer page, Integer size) {
    final var pageable =
        PageRequest.of(
            page != null ? page : 0, size != null ? size : 10, Sort.Direction.DESC, "createdAt");

    return couponDAO.findAll(pageable).toList();
  }

  public Coupon getCoupon(Long id) {
    return couponDAO.findById(id).orElseThrow(BusinessExceptions.COUPON_NOT_FOUND::get);
  }

  public void deleteCoupon(Long id) {
    var coupon = getCoupon(id);
    coupon.setStatus(CouponStatusEnum.REMOVED);
    couponDAO.save(coupon);
  }

  public Coupon create(CouponDTO couponDTO) {
    var couponEntity = modelMapper.map(couponDTO, Coupon.class);

    CouponSpecificationFactory.constructForCreateValidation(couponDTO.getCode())
        .isSatisfiedBy(couponDTO)
        .ifPresent(SpecificationExceptionUtil::throwBusinessException);

    return couponDAO.save(couponEntity);
  }

  public Coupon update(CouponDTO couponDTO) {
    return this.create(couponDTO);
  }

  public void throwExceptionIfCouponNotValid(CouponValidationRequest request, String code) {
    CouponDTO couponDTO = getCouponDTOByCode(code);

    CouponSpecificationFactory.constructForConsumeValidation(request.getAmount())
        .isSatisfiedBy(couponDTO)
        .ifPresent(SpecificationExceptionUtil::throwBusinessException);
  }

  @Transactional
  public void redeemCoupon(String code) {
    logger.info("Redeeming coupon with code: ");

    CouponDTO couponDTO = getCouponDTOByCode(code);

    CouponSpecificationFactory.constructForRedeemValidation()
        .isSatisfiedBy(couponDTO)
        .ifPresent(SpecificationExceptionUtil::throwBusinessException);

    try {
      couponDAO.redeem(code);
    } catch (DAOException daoException) {
      if (daoException
          .getMessage()
          .equals(DAOExceptions.UNSIGNED_VALUE_OUT_RANGE.get().getMessage())
      ) {
        throw BusinessExceptions.COUPON_SOLD_OUT.get();
      }

      throw BusinessExceptions.GENERIC_ERROR.get();
    }
  }

  @Transactional
  public void refundCoupon(String code) {
    logger.info("Refunding coupon with code: " + code);

    CouponDTO couponDTO = getCouponDTOByCode(code);

    CouponSpecificationFactory.constructForRedeemValidation()
        .isSatisfiedBy(couponDTO)
        .ifPresent(SpecificationExceptionUtil::throwBusinessException);

    couponDAO.refund(code);
  }

  private CouponDTO getCouponDTOByCode(String code) {
    var coupon = couponDAO.findByCode(code);
    return coupon != null ? modelMapper.map(coupon, CouponDTO.class) : null;
  }
}
