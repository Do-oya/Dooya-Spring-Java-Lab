package org.example.springauthpractice.common.exception;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("[IllegalArgumentException] 예외 발생: {}", ex.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorMessage = ex.getMessage();

        return ResponseEntity.status(status).body(ErrorResponse.of(errorMessage));
    }

    // @RequestParam, @PathVariable의 유효성 검사를 수행
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("[Validated] 유효성 검사 예외 발생 msg:{}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        List<ErrorResponse.ValidationError> validationErrors = extractValidationErrors(ex.getConstraintViolations());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorCode, validationErrors));
    }

    // 커스템 예외를 검사를 수행
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException ex) {
        log.error("예외 발생 msg:{}", ex.getErrorCode().getMessage());
        ErrorCode errorCode = ex.getErrorCode();
        String errorMessage = errorCode.getMessage();
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorMessage));
    }

    //  JSON 데이터를 DTO로 변환하는 과정에서 유효성 검사를 수행
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        List<ErrorResponse.ValidationError> validationErrors = extractValidationErrors(ex.getBindingResult());
        log.error("[Valid] 유효성 검사 예외 발생 errors: {}", validationErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorCode, validationErrors));
    }

    // json 형식 검사를 수행
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_JSON_FORMAT;
        String errorMessage = errorCode.getMessage() + " : " + ex.getMessage();
        log.error("예외 발생 msg: {}", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorMessage));
    }

    private List<ErrorResponse.ValidationError> extractValidationErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());
    }

    private List<ErrorResponse.ValidationError> extractValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());
    }
}

