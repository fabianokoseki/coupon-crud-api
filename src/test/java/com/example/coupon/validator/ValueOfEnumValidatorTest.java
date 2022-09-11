package com.example.coupon.validator;

import org.hibernate.validator.constraintvalidation.HibernateConstraintViolationBuilder;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Payload;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValueOfEnumValidatorTest {

  @Mock private ConstraintValidatorContextImpl constraintValidatorContext;

  @Mock private ConstraintViolationCreationContext constraintViolationCreationContext;

  @Mock private PathImpl pathImpl;

  @Mock private HibernateConstraintViolationBuilder constraintViolationBuilder;

  private ValueOfEnumValidator valueOfEnumValidator;

  @BeforeAll
  public void setUp() {
    final var mustBeOneOfTheEnum = new MustBeOneOfTheEnumImpl();
    valueOfEnumValidator = new ValueOfEnumValidator();
    valueOfEnumValidator.initialize(mustBeOneOfTheEnum);
  }

  @Test
  public void testValueOfEnumValidatorValid() {
    final var result =
        valueOfEnumValidator.isValid(TestEnum.IPSUM.toString(), constraintValidatorContext);

    assertTrue(result);
  }

  @Test
  public void testValueOfEnumValidatorInvalid() {
    String invalidEnumValue = "INVALID_ENUM_VALUE";
    String fieldName = "fieldTest";

    stubDependencies(invalidEnumValue, fieldName);

    final var result = valueOfEnumValidator.isValid(invalidEnumValue, constraintValidatorContext);

    assertFalse(result);
  }

  private void stubDependencies(String invalidEnumValue, String fieldName) {
    when(pathImpl.toString()).thenReturn(fieldName);
    when(constraintViolationCreationContext.getPath()).thenReturn(pathImpl);
    when(constraintValidatorContext.getConstraintViolationCreationContexts())
        .thenReturn(List.of(constraintViolationCreationContext));
    when(constraintValidatorContext.buildConstraintViolationWithTemplate(
            ValueOfEnumValidator.getMessageTemplate(
                invalidEnumValue,
                fieldName,
                Stream.of(TestEnum.values()).map(Enum::name).collect(Collectors.toList()))))
        .thenReturn(constraintViolationBuilder);
  }

  private static class MustBeOneOfTheEnumImpl implements MustBeOneOfTheEnum {
    @Override
    public Class<? extends Enum<?>> enumClass() {
      return TestEnum.class;
    }

    @Override
    public String message() {
      return "Error message enum validator";
    }

    @Override
    public Class<?>[] groups() {
      return new Class[0];
    }

    @Override
    public Class<? extends Payload>[] payload() {
      return new Class[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return null;
    }
  }

  private enum TestEnum {
    LOREM,
    IPSUM
  }
}
