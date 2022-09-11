package com.example.coupon.dto;

import com.example.coupon.enums.CouponStatusEnum;
import com.example.coupon.enums.DiscountTypeEnum;
import com.example.coupon.validator.MustBeOneOfTheEnum;
import com.example.coupon.validator.StartAtBeforeFinishAt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@StartAtBeforeFinishAt
@Builder
public class CouponDTO {

  private Long id;

  @NotEmpty private String name;

  @NotEmpty private String code;

  private String description;

  @Min(value = 0, message = "Quantity is required")
  private Integer quantity;

  private Integer usages;

  private Long amount;

  private Long minimumAmountToApply;

  @MustBeOneOfTheEnum(enumClass = DiscountTypeEnum.class)
  private String discountType;

  @MustBeOneOfTheEnum(enumClass = CouponStatusEnum.class)
  private String status;

  private LocalDateTime startAt;

  private LocalDateTime finishAt;

  private LocalDate createdAt;

  private LocalDate updatedAt;
}
