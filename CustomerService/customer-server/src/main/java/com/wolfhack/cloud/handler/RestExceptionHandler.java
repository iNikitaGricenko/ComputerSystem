package com.wolfhack.cloud.handler;

import com.wolfhack.cloud.customer.exception.CustomerNotFoundException;
import com.wolfhack.cloud.customer.exception.CustomerOrderNotFoundException;
import com.wolfhack.cloud.customer.exception.ProductNotFoundException;
import com.wolfhack.cloud.handler.error.ErrorBody;
import com.wolfhack.cloud.handler.error.ValidationErrorBody;
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
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(toList());
		ValidationErrorBody body = new ValidationErrorBody(status, request, errors);
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleViolationAccess(ValidationException exception, WebRequest request) {
		return handleException(exception, request, FORBIDDEN);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> handleCustomerNotFound(CustomerNotFoundException exception, WebRequest request) {
		return handleException(exception, request, exception.getStatus());
	}

	@ExceptionHandler(CustomerOrderNotFoundException.class)
	public ResponseEntity<Object> handleOrderNotFound(CustomerOrderNotFoundException exception, WebRequest request) {
		return handleException(exception, request, exception.getStatus());
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException exception, WebRequest request) {
		return handleException(exception, request, exception.getStatus());
	}

	private ResponseEntity<Object> handleException(Exception exception, WebRequest request, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		ErrorBody body = new ErrorBody(status, request, exception.getMessage());
		return handleExceptionInternal(exception, body, headers, status, request);
	}

}
