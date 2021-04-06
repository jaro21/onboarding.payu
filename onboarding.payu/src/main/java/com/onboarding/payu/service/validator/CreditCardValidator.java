package com.onboarding.payu.service.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreditCardValidator {

	@Value("${credit.card.mastercard}")
	private String visa;

	//@Value("${credit.card}")
	//private Map<String, String> creditCards;

	public void runValidations(final CreditCardDto creditCardDto) throws RestApplicationException{
		isPeriodValid(creditCardDto.getExpirationDate());
		getPaymentMethod(creditCardDto.getNumber());
	}

	public void isPeriodValid(final String period) throws RestApplicationException {

		final LocalDate periodo = stringToLocalDate(period);
		if (periodo.isBefore(LocalDate.now())) {
			throw new RestApplicationException(ExceptionCodes.PERIOD_INVALID.getCode(), ExceptionCodes.PERIOD_INVALID.getMessage());
		}
	}

	public LocalDate stringToLocalDate(final String period) throws RestApplicationException {

		try {
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy/MM")
																		.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
																		.toFormatter();
			return LocalDate.parse(period, formatter);
		} catch (final DateTimeParseException e) {
			throw new RestApplicationException(ExceptionCodes.PERIOD_FORMAT_INVALID.getCode(),
											   ExceptionCodes.PERIOD_FORMAT_INVALID.getMessage());
		}
	}

	public String getPaymentMethod(final String cardNumber){
		Pattern patron = Pattern.compile(visa);
		Matcher matcher = patron.matcher(cardNumber);
		boolean esCoincidente = matcher.find();

		return null;
	}
}
