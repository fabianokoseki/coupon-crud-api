package com.example.coupon.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationResult {
  @Getter private List<String> errors = new ArrayList<>();

  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  public void addError(String error) {
    errors.add(error);
  }
}
