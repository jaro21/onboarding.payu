package com.onboarding.payu.exception;

import java.time.LocalDateTime;

import com.onboarding.payu.controller.CreditCardController;
import com.onboarding.payu.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Credit Card's Exception handler
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice(assignableTypes = CreditCardController.class)
public class CreditCardExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Customize the response for {@link MethodArgumentNotValidException}.
	 *
	 * @param ex      the exception {@link MethodArgumentNotValidException}
	 * @param headers the {@link HttpHeaders} to be written to the response
	 * @param status  the selected response {@link HttpStatus}
	 * @param request the current {@link WebRequest}
	 * @return a {@code ResponseEntity} instance
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
																  final @NonNull HttpHeaders headers,
																  final @NonNull HttpStatus status,
																  final @NonNull WebRequest request) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							 .body(ResponseDto.builder()
											  .message(ex.getBindingResult().getFieldError().getDefaultMessage())
											  .responseCode(HttpStatus.BAD_REQUEST.value())
											  .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
											  .timestamp(LocalDateTime.now())
											  .build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
																		 HttpStatus status, WebRequest request) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
							 .body(ResponseDto.builder()
											  .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
											  .responseCode(HttpStatus.METHOD_NOT_ALLOWED.value())
											  .status(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
											  .timestamp(LocalDateTime.now())
											  .build());
	}

	/**
	 * Handle an {@link RestApplicationException}.
	 *
	 * @param ex of {@link RestApplicationException} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(RestApplicationException.class)
	public ResponseEntity<ResponseDto> handleRestApplicationException(final RestApplicationException ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
							 .body(ResponseDto.builder()
											  .message(ex.getMessage())
											  .exceptionCode(ex.getCode())
											  .responseCode(HttpStatus.CONFLICT.value())
											  .status(HttpStatus.CONFLICT.getReasonPhrase())
											  .timestamp(LocalDateTime.now())
											  .build());
	}

	/**
	 * Handler the generic exception.
	 *
	 * @param e {@link Exception} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleException(final Exception e) {

		log.error("Unhandled exception : ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(ResponseDto.builder()
											  .message(e.getMessage())
											  .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
											  .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
											  .timestamp(LocalDateTime.now())
											  .build());
	}

	/**
	 * Handler the generic exception.
	 *
	 * @param e {@link RuntimeException} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseDto> handleRuntimeException(final RuntimeException e) {

		log.error("Unhandled exception : ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(ResponseDto.builder()
											  .message(e.getMessage())
											  .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
											  .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
											  .timestamp(LocalDateTime.now())
											  .build());
	}
}
