package com.example.coupon.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StartAtBeforeFinishAtValidator.class})
@Documented
public @interface StartAtBeforeFinishAt {
  String message() default "startAt needs to be before finishAt";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
