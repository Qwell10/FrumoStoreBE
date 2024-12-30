package com.FrumoStore.service;

import com.FrumoStore.consumablesRepository.ConsumablesRepository;
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
class ConsumablesServiceTest {

    @Mock
    ConsumablesRepository consumablesRepository;

    @Mock
    DateFormatChecker dateFormatChecker;

    @InjectMocks
    ConsumablesService consumablesService;

    @Test
    public void incomeToWarehouse_saveWithNullDate() {
        consumablesService.incomeToWarehouse(800, null);

        verify(dateFormatChecker, never()).parseStringToLocalDate(any());
    }

    @Test
    public void incomeToWarehouse_saveWithAllParams() {
        LocalDate expectedDate = LocalDate.of(2024, 6, 1);

        when(dateFormatChecker.parseStringToLocalDate(any())).thenReturn(expectedDate);

        consumablesService.incomeToWarehouse(700, "01-06-2024");

        verify(consumablesRepository).save(argThat(
                entity -> entity.getWeight() == 700 && entity.getDate().equals(expectedDate)));
    }



    @Test
    public void incomeToWarehouse_noStockInDB() {
        when(consumablesService.getStockBalance()).thenReturn(0.0);

        consumablesService.incomeToWarehouse(600, "01-06-2024");

        verify(consumablesRepository).save(argThat(
                entity -> entity.getStockBalance() == 600));
    }

    @Test
    public void incomeToWarehouse_StockInDB() {
        when(consumablesService.getStockBalance()).thenReturn(305.5);

        consumablesService.incomeToWarehouse(300, "01-06-2024");

        verify(consumablesRepository).save(argThat(
                entity -> entity.getStockBalance() == 605.5));
    }


    @Test
    public void incomeToWarehouse_throwIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> consumablesService.incomeToWarehouse(-50, null));

        assertEquals("Weight cannot be negative.", e.getMessage());
    }
}