package com.FrumoStore.utility;

import com.FrumoStore.exception.DateInvalidFormatException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateFormatChecker {

    public LocalDate parseStringToLocalDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DateInvalidFormatException("Invalid date format -> dd-MM-yyyy");
        }
    }
}
