package com.FrumoStore.service;

import com.FrumoStore.repository.IncomeGoodsRepository;
import com.FrumoStore.repository.OutcomeGoodsRepository;
import com.FrumoStore.repository.StockBalanceRepository;
import com.FrumoStore.repository.WasteRepository;
import com.FrumoStore.exception.InsufficientStockException;
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
class GoodsServiceTest {

    @Mock
    IncomeGoodsRepository incomeGoodsRepository;

    @Mock
    OutcomeGoodsRepository outcomeGoodsRepository;

    @Mock
    StockBalanceRepository stockBalanceRepository;

    @Mock
    WasteRepository wasteRepository;

    @Mock
    DateFormatChecker dateFormatChecker;

    @InjectMocks
    GoodsService goodsService;


    LocalDate expectedDate = LocalDate.of(2024, 6, 1);

    @Test
    public void incomeToWarehouse_saveWithNullDate() {
        goodsService.incomeToWarehouse(800, null);

        verify(dateFormatChecker, never()).parseStringToLocalDate(any());
    }

    @Test
    public void incomeToWarehouse_saveWithoutStockBalance() {
        when(dateFormatChecker.parseStringToLocalDate(any())).thenReturn(expectedDate);

        goodsService.incomeToWarehouse(700, "01-06-2024");

        verify(incomeGoodsRepository).save(argThat(
                entity -> entity.getWeight() == 700 && entity.getDate().equals(expectedDate)));
        verify(stockBalanceRepository).save(argThat(entity -> entity.getReceiptDate().equals(expectedDate) && entity.getStockBalance() == 700));
    }

    @Test
    public void incomeToWarehouse_saveWithStockBalance() {
        when(dateFormatChecker.parseStringToLocalDate(any())).thenReturn(expectedDate);
        when(stockBalanceRepository.findLastStockBalance()).thenReturn(200.5);

        goodsService.incomeToWarehouse(700, "01-06-2024");

        verify(incomeGoodsRepository).save(argThat(
                entity -> entity.getWeight() == 700 && entity.getDate().equals(expectedDate)));
        verify(stockBalanceRepository).save(argThat(entity -> entity.getReceiptDate().equals(expectedDate) && entity.getStockBalance() == 900.5));
    }

    @Test
    public void outcomeFromWarehouse_happyScenario() {
        when(stockBalanceRepository.findLastStockBalance()).thenReturn(200.5);

        goodsService.outcomeFromWarehouse(6, null);

        verify(outcomeGoodsRepository).save(argThat(
                entity -> entity.getBoxes() == 6 &&
                        entity.getTotalWeight() == 36));
        verify(stockBalanceRepository).save(argThat(entity -> entity.getStockBalance() == 164.5));
    }

    @Test
    public void waste_happyScenario() {
        when(stockBalanceRepository.findLastStockBalance()).thenReturn(100.5);

        goodsService.waste(50.5, null);

        verify(wasteRepository).save(argThat(entity -> entity.getWeight() == 50.5));
        verify(stockBalanceRepository).save(argThat(entity -> entity.getStockBalance() == 50));
    }

    @Test
    public void incomeToWarehouse_throwIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> goodsService.incomeToWarehouse(-50, null));

        assertEquals("Weight cannot be negative.", e.getMessage());
    }

    @Test
    public void outcomeFromWarehouse_throwInsufficientStockException() {
        when(stockBalanceRepository.findLastStockBalance()).thenReturn(50.5);

        Exception e = assertThrows(InsufficientStockException.class, () -> goodsService.outcomeFromWarehouse(20, null));

        assertEquals("Insufficient stock", e.getMessage());
    }

    @Test
    public void waste_throwInsufficientStockException() {
        when(stockBalanceRepository.findLastStockBalance()).thenReturn(50.5);

        Exception e = assertThrows(InsufficientStockException.class, () -> goodsService.waste(70, null));

        assertEquals("Insufficient stock", e.getMessage());
    }
}