package com.wolfhack.cloud.oauth2.exception.handler;

import com.wolfhack.cloud.oauth2.exception.UserExistsException;
import com.wolfhack.cloud.oauth2.exception.UserNotFoundException;
import com.wolfhack.cloud.oauth2.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.oauth2.exception.handler.error.ValidationErrorBody;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(toList());
        ValidationErrorBody body = new ValidationErrorBody(status, request, errors);
        return handleExceptionInternal(exception, body, headers, status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleViolationAccess(ValidationException exception, WebRequest request) {
        return handleException(exception, request, FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExists(UserExistsException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    private ResponseEntity<Object> handleException(Exception exception, WebRequest request, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        ErrorBody body = new ErrorBody(status, request, exception.getMessage());
        return handleExceptionInternal(exception, body, headers, status, request);
    }

}
