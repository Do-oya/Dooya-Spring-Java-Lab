package org.example.springauthpractice.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import lombok.Builder;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Builder
public record ErrorResponse(
        boolean status,
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ValidationError> validationErrors
) {
    public static ErrorResponse of(ErrorCode errorCode, List<ValidationError> validationErrors){
        return ErrorResponse.builder()
                .status(false)
                .message(errorCode.getMessage())
                .validationErrors(Optional.ofNullable(validationErrors).orElse(List.of()))
                .build();
    }

    public static ErrorResponse of(String errorMessage) {
        return ErrorResponse.builder()
                .status(false)
                .message(errorMessage)
                .build();
    }

    public record ValidationError(String field, String message) {
        public static ValidationError of(final FieldError fieldError) {
            return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        public static ValidationError of(final ConstraintViolation<?> constraintViolation) {
            return new ValidationError(
                    extractFieldName(constraintViolation.getPropertyPath()), constraintViolation.getMessage()
            );
        }

        private static String extractFieldName(Path propertyPath) {
            String[] pathElements = propertyPath.toString().split("\\.");
            return pathElements[pathElements.length - 1];
        }
    }
}


