package com.wolfhack.cloud.product.exception.handler;

import com.wolfhack.cloud.product.exception.*;
import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.exception.handler.error.ValidationErrorBody;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
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

    @ExceptionHandler(CpuNotFoundException.class)
    public ResponseEntity<Object> handleCpuNotFound(CpuNotFoundException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    @ExceptionHandler(GpuNotFoundException.class)
    public ResponseEntity<Object> handleGpuNotFound(GpuNotFoundException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    @ExceptionHandler(MotherboardNotFoundException.class)
    public ResponseEntity<Object> handleMotherboardNotFound(MotherboardNotFoundException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    @ExceptionHandler(RamNotFoundException.class)
    public ResponseEntity<Object> handleGpuNotFound(RamNotFoundException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    @ExceptionHandler(SsdNotFoundException.class)
    public ResponseEntity<Object> handleSsdNotFound(SsdNotFoundException exception, WebRequest request) {
        return handleException(exception, request, exception.getStatus());
    }

    private ResponseEntity<Object> handleException(Exception exception, WebRequest request, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        ErrorBody body = new ErrorBody(status, request, exception.getMessage());
        return handleExceptionInternal(exception, body, headers, status, request);
    }

}
