package com.onboarding.payu.service.validator;

import static java.lang.String.format;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.model.CreditCartType;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import org.springframework.stereotype.Component;

/**
 * Credit card validator
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CreditCardValidator {

	public void runValidations(final CreditCardDto creditCardDto) {

		isPeriodValid(creditCardDto.getExpirationDate());
		isPaymentMethodValid(creditCardDto);
	}

	private void isPaymentMethodValid(final CreditCardDto creditCardDto) {

		final Optional<CreditCartType> creditCartType = getPaymentMethod(creditCardDto.getNumber());
		if (!creditCartType.isPresent()) {
			throw new BusinessAppException(ExceptionCodes.CREDIT_CARD_INVALID.getCode(),
										   ExceptionCodes.CREDIT_CARD_INVALID.getMessage());
		}
		if (!creditCartType.get().getNameCard().equals(creditCardDto.getPaymentMethod())) {
			throw new BusinessAppException(ExceptionCodes.PAYMENT_METHOD_IVALID.getCode(),
										   format(ExceptionCodes.PAYMENT_METHOD_IVALID.getMessage(), creditCardDto.getPaymentMethod()));
		}
	}

	public void isPeriodValid(final String period) {

		final LocalDate periodo = stringToLocalDate(period);
		if (periodo.isBefore(LocalDate.now())) {
			throw new BusinessAppException(ExceptionCodes.PERIOD_INVALID.getCode(), ExceptionCodes.PERIOD_INVALID.getMessage());
		}
	}

	public LocalDate stringToLocalDate(final String period) {

		try {
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy/MM")
																		.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
																		.toFormatter();
			return LocalDate.parse(period, formatter);
		} catch (final DateTimeParseException e) {
			throw new BusinessAppException(ExceptionCodes.PERIOD_FORMAT_INVALID.getCode(),
										   ExceptionCodes.PERIOD_FORMAT_INVALID.getMessage());
		}
	}

	public Optional<CreditCartType> getPaymentMethod(final String cardNumber) {

		for (CreditCartType creditCartType : CreditCartType.values()) {
			Pattern patron = Pattern.compile(creditCartType.getPattern());
			Matcher matcher = patron.matcher(cardNumber);

			if (matcher.find()) {
				return Optional.of(creditCartType);
			}
		}

		return Optional.empty();
	}
}
