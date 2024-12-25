package com.FrumoStore.utility;

import com.FrumoStore.exception.DateInvalidFormatException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @see DateFormatChecker
 */
class DateFormatCheckerTest {

    DateFormatChecker dateFormatChecker = new DateFormatChecker();

    @Test
    public void checkDateFormat_happyScenario() {
        LocalDate expectedValue = LocalDate.of(2021, 8, 18);

        LocalDate parsedDate = dateFormatChecker.parseStringToLocalDate("18-08-2021");

        assertEquals(parsedDate, expectedValue);
    }

    @Test
    public void checkDateFormat_badFormat() {
        Exception e = assertThrows(DateInvalidFormatException.class,
                () -> dateFormatChecker.parseStringToLocalDate("18.8.2021"));
        assertEquals("Invalid date format -> dd-MM-yyyy", e.getMessage());
    }
}