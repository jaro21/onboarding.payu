package com.onboarding.payu.controller.handler;

import com.onboarding.payu.controller.CreditCardController;
import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
											  .build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
																  HttpStatus status, WebRequest request) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							 .body(ResponseDto.builder()
											  .message(ex.getMessage())
											  .build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
																		 HttpStatus status, WebRequest request) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
							 .body(ResponseDto.builder()
											  .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
											  .build());
	}

	/**
	 * Handle an {@link BusinessAppException}.
	 *
	 * @param ex of {@link BusinessAppException} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(BusinessAppException.class)
	public ResponseEntity<ResponseDto> handleBusinessAppException(final BusinessAppException ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
							 .body(ResponseDto.builder()
											  .message(ex.getMessage())
											  .errorCode(ex.getCode())
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
											  .build());
	}
}
