package com.example.coupon.validator;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<MustBeOneOfTheEnum, CharSequence> {
  private List<String> acceptedValues;

  @Override
  public void initialize(MustBeOneOfTheEnum annotation) {
    acceptedValues =
        Stream.of(annotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (value == null || acceptedValues.contains(value.toString())) {
      return true;
    }

    var fieldName = getFieldName(context);
    context.disableDefaultConstraintViolation();

    context
        .buildConstraintViolationWithTemplate(
            getMessageTemplate(value, fieldName.toString(), acceptedValues))
        .addConstraintViolation();

    return false;
  }

  public static String getMessageTemplate(
      CharSequence value, String fieldName, List<String> acceptedValues) {
    return String.format(
        "Invalid value '%s' for field %s. Accepted values: %s", value, fieldName, acceptedValues);
  }

  private PathImpl getFieldName(ConstraintValidatorContext context) {
    return ((ConstraintValidatorContextImpl) context)
        .getConstraintViolationCreationContexts()
        .get(0)
        .getPath();
  }
}
