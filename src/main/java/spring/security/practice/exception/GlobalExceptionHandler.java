package spring.security.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.security.practice.base.ResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseError<Object> responseError = ResponseError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .errors(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
