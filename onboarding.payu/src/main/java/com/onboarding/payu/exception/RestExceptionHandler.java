package com.onboarding.payu.exception;

import com.onboarding.payu.controller.ProductController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackageClasses = {ProductController.class})
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(request.toString(), ex);
		return new ResponseEntity("Malformed JSON request", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler the illegal argument exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity handlerIllegalArgumentException(IllegalArgumentException e) {
		return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler the illegal argument exception
	 */
	@ExceptionHandler(RestApplicationException.class)
	public ResponseEntity handlerRestApplicationException(RestApplicationException e) {
		return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler the generic exception.
	 *
	 * @param e {@link Exception} with the information about the error.
	 * @return {@link ResponseEntity} object with the formatted error information.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity handlerException(final Exception e) {
		log.error("Internal Server Error : ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}


}
