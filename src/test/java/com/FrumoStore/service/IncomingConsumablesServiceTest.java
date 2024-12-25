package com.FrumoStore.service;

import com.FrumoStore.incomingConsumablesRepository.IncomingConsumablesRep;
import com.FrumoStore.utility.DateFormatChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncomingConsumablesServiceTest {

    @Mock
    IncomingConsumablesRep incomingConsumablesRep;

    @Mock
    DateFormatChecker dateFormatChecker;

    @InjectMocks
    IncomingConsumablesService incomingConsumablesService;

    @Test
    public void incomeToWarehouse_saveWithNullDate() {
        incomingConsumablesService.incomeToWarehouse(800, null);

        verify(dateFormatChecker, never()).parseStringToLocalDate(any());
    }

    @Test
    public void incomeToWarehouse_saveWithAllParams() {
        LocalDate expectedDate = LocalDate.of(2024, 6, 1);

        when(dateFormatChecker.parseStringToLocalDate(any())).thenReturn(expectedDate);

        incomingConsumablesService.incomeToWarehouse(700, "01-06-2024");

        verify(incomingConsumablesRep).save(argThat(
                entity -> entity.getWeight() == 700 && entity.getDate().equals(expectedDate)));
    }

    @Test
    public void incomeToWarehouse_throwIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> incomingConsumablesService.incomeToWarehouse(-50, null));

        assertEquals("Weight cannot be negative.", e.getMessage());
    }
}