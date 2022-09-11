package com.example.coupon.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HashMap<String, Object>> handleAnyUnknownException(Exception ex) {
    var genericError = BusinessExceptions.GENERIC_ERROR.get();
    HashMap<String, Object> response =
        defaultResponse(genericError.code(), genericError.message(), null);
    return new ResponseEntity<>(response, new HttpHeaders(), 500);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<HashMap<String, Object>> handleBusinessException(BusinessException ex) {
    HashMap<String, Object> response = defaultResponse(ex.code(), ex.message(), ex.details());

    return new ResponseEntity<>(response, new HttpHeaders(), 422);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<HashMap<String, Object>> handleInputValidation(
      MethodArgumentNotValidException ex) {
    List<Map<String, String>> details = getDetails(ex);

    var invalidParamsException = BusinessExceptions.INVALID_PARAMS.get();

    HashMap<String, Object> response =
        defaultResponse(invalidParamsException.code(), invalidParamsException.message(), details);

    return new ResponseEntity<>(response, new HttpHeaders(), 422);
  }

  public static List<Map<String, String>> getDetails(MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getAllErrors().stream()
        .map(obj -> Map.of("reason", obj.getDefaultMessage()))
        .collect(Collectors.toList());
  }

  private LinkedHashMap<String, Object> defaultResponse(
      String code, String message, Object details) {
    var response = new LinkedHashMap<String, Object>();
    response.put("code", code);
    response.put("message", message);

    if (details != null) response.put("details", details);

    return response;
  }
}
