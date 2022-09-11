package com.example.coupon.entity;

import com.example.coupon.enums.CouponStatusEnum;
import com.example.coupon.enums.DiscountTypeEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class Coupon extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  private String code;

  private Long amount;

  private Long minimumAmountToApply;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime startAt;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime finishAt;

  private String description;

  @Min(0)
  private int quantity;

  private int usages;

  @Enumerated(EnumType.STRING)
  private CouponStatusEnum status;

  @Enumerated(EnumType.STRING)
  private DiscountTypeEnum discountType;

  @Version private final Integer version = 0;
}
