package com.example.coupon.specification;

public interface Specification<T> {
  SpecificationResult isSatisfiedBy(T element);
}
