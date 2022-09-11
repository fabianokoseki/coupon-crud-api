package com.example.coupon.specification;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AndSpecification<T> {

  private final List<Specification<T>> specList;

  public Optional<SpecificationResult> isSatisfiedBy(T element) {
    return specList.stream()
        .map(spec -> spec.isSatisfiedBy(element))
        .filter(SpecificationResult::hasErrors)
        .findFirst();
  }
}
