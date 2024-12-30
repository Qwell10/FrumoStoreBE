package com.FrumoStore.service;

import com.FrumoStore.entity.ConsumablesEntity;
import com.FrumoStore.consumablesRepository.ConsumablesRepository;
import com.FrumoStore.utility.DateFormatChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ConsumablesService {

    @Autowired
    ConsumablesRepository consumablesRepository;

    @Autowired
    DateFormatChecker dateFormatChecker;

    public void incomeToWarehouse(double weight, String date) {
        Double newStockBalance = getStockBalance() + weight;

        if (weight <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        } else if (weight > 0 && date == null) {
            consumablesRepository.save(new ConsumablesEntity(weight, LocalDate.now(), newStockBalance));
        } else if (weight > 0 && !date.isEmpty()) {
            LocalDate incomeToWarehouseDate = dateFormatChecker.parseStringToLocalDate(date);
            consumablesRepository.save(new ConsumablesEntity(weight, incomeToWarehouseDate, newStockBalance));
        }
    }


    public Double getStockBalance() {
        Double stockBalance = consumablesRepository.findLastStockBalance();
        if (stockBalance == null || stockBalance == 0) {
            return 0.0;
        } else return stockBalance;
    }
}


