package com.example.coupon.exception;

import com.example.coupon.dto.CouponDTO;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerAdvisorTest {

  @Test
  public void testThatAnyUnmappedExceptionWillReturnGenericMessageAndCodeAnd500StatusCode() {
    final var controllerAdvisor = new ControllerAdvisor();
    final var genericError = BusinessExceptions.GENERIC_ERROR.get();
    final var exception = new Exception(genericError.getMessage());
    final var result = controllerAdvisor.handleAnyUnknownException(exception);

    final var expectedBody =
        Map.of(
            "code", genericError.code(),
            "message", genericError.message());

    assertEquals(500, result.getStatusCodeValue());
    assertEquals(expectedBody, result.getBody());
  }

  @Test
  public void testBusinessExceptionWillReturnAExpectedBodyAndStatusCode() {
    final var controllerAdvisor = new ControllerAdvisor();
    final var invalidParamException = BusinessExceptions.INVALID_PARAMS.get();
    final var result = controllerAdvisor.handleBusinessException(invalidParamException);

    final var expectedBody =
        Map.of(
            "code", invalidParamException.code(),
            "message", invalidParamException.message());

    assertEquals(expectedBody, result.getBody());
    assertEquals(422, result.getStatusCodeValue());
  }

  @Test
  public void testConstraintValidatorExceptionWillShowExpectedBodyAndStatusCode() {
    final var controllerAdvisor = new ControllerAdvisor();
    final var invalidParamException = BusinessExceptions.INVALID_PARAMS.get();

    String objectName = CouponDTO.class.getName();
    final var bindingResult = new BeanPropertyBindingResult(null, objectName);
    final var objectError =
        new ObjectError(
            objectName,
            "Invalid value 'HUE' for field discountType. Accepted values: [PERCENTAGE, FIXED]");
    bindingResult.addError(objectError);

    final var methodArgumentNotValid = new MethodArgumentNotValidException(null, bindingResult);
    final var result = controllerAdvisor.handleInputValidation(methodArgumentNotValid);

    final var details = ControllerAdvisor.getDetails(methodArgumentNotValid);

    final var expectedBody =
        Map.of(
            "code", invalidParamException.code(),
            "message", invalidParamException.message(),
            "details", details);

    assertEquals(422, result.getStatusCodeValue());
    assertEquals(expectedBody, result.getBody());
  }
}
