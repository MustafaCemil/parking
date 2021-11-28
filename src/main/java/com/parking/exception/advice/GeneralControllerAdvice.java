package com.parking.exception.advice;

import com.parking.enums.ResponseType;
import com.parking.exception.OperationResutlException;
import com.parking.model.response.OperationResult;
import com.parking.util.GeneralOperations;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GeneralControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    final Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getAllErrors()
        .forEach(error -> {
          String fieldName = ((FieldError) error).getField();
          String errorMessage = error.getDefaultMessage();
          errors.put(fieldName, errorMessage);
        });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handle(ConstraintViolationException ex) {
    final Map<String, String> errors = new HashMap<>();

    if (!CollectionUtils.isEmpty(ex.getConstraintViolations())) {
      ex.getConstraintViolations()
          .forEach(error -> {
            String fieldName = null;
            if (error.getPropertyPath() != null && ((PathImpl) error.getPropertyPath()).getLeafNode() != null) {
              fieldName = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            }
            String errorMessage = error.getMessageTemplate();
            errors.put(fieldName != null ? fieldName : "Key: ", errorMessage);
          });
    }

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(OperationResutlException.class)
  public ResponseEntity<OperationResult> handle(OperationResutlException ex) {
    return new ResponseEntity<>(ex.getOperationResult(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<OperationResult> handle(Exception ex) {
    log.error("Error detail: " + GeneralOperations.exceptionConvertToString(ex));
    return new ResponseEntity<>(
        OperationResult.builder()
            .operationCode(ResponseType.ERORR.name())
            .operationMessage(ResponseType.ERORR.getMessage())
            .build(),
        HttpStatus.BAD_REQUEST);
  }
}
