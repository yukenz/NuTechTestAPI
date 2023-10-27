package nutech.awan.ppob.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationAdvice {

    /* Validasi Form dengan ConstraintViolation */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<WebResponse<String>> constraintViolationException(ConstraintViolationException ex) {

        /* Constraint Violations String Builder*/
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String errors = violations.stream().map(violation -> {
            return String.format("%s (%s)", violation.getPropertyPath(), violation.getMessage());
        }).collect(Collectors.joining(","));

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(httpStatus.value())
                .message(errors)
                .build();

        return ResponseEntity.status(httpStatus).body(webResponse);
    }

}
