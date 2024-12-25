package com.FrumoStore.service;

import com.FrumoStore.entity.IncomingConsumablesEntity;
import com.FrumoStore.incomingConsumablesRepository.IncomingConsumablesRep;
import com.FrumoStore.utility.DateFormatChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IncomingConsumablesService {

    @Autowired
    IncomingConsumablesRep incomingConsumablesRep;

    @Autowired
    DateFormatChecker dateFormatChecker;

    public void incomeToWarehouse(double weight, String date) {
        if (weight > 0 && date == null) {
            incomingConsumablesRep.save(new IncomingConsumablesEntity(weight, LocalDate.now()));
        } else if (weight > 0 && !date.isEmpty()) {
            LocalDate incomeToWarehouseDate = dateFormatChecker.parseStringToLocalDate(date);
            incomingConsumablesRep.save(new IncomingConsumablesEntity(weight, incomeToWarehouseDate));
        } else throw new IllegalArgumentException("Weight cannot be negative.");
    }
}


