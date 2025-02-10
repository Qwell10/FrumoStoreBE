package com.FrumoStore.service;

import com.FrumoStore.repository.OutcomeGoodsRepository;
import com.FrumoStore.repository.StockBalanceRepository;
import com.FrumoStore.repository.WasteRepository;
import com.FrumoStore.entity.IncomeGoodsEntity;
import com.FrumoStore.repository.IncomeGoodsRepository;
import com.FrumoStore.entity.OutcomeGoodsEntity;
import com.FrumoStore.entity.StockBalanceEntity;
import com.FrumoStore.entity.WasteEntity;
import com.FrumoStore.exception.InsufficientStockException;
import com.FrumoStore.utility.DateFormatChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GoodsService {

    @Autowired
    IncomeGoodsRepository incomeGoodsRepository;

    @Autowired
    OutcomeGoodsRepository outcomeGoodsRepository;

    @Autowired
    StockBalanceRepository stockBalanceRepository;

    @Autowired
    WasteRepository wasteRepository;

    @Autowired
    DateFormatChecker dateFormatChecker;

    public void incomeToWarehouse(double weight, String date) {
        LocalDate incomeGoodsDate = validateDate(date);
        double incomeGoodsWeight = validateWeight(weight);
        double stockBalance = getStockBalance();

        incomeGoodsRepository.save(new IncomeGoodsEntity(incomeGoodsWeight, incomeGoodsDate));
        stockBalanceRepository.save(new StockBalanceEntity(incomeGoodsDate, stockBalance + incomeGoodsWeight));
    }


    public void outcomeFromWarehouse(int boxes, String date) {
        double totalWeight = boxes * 6;
        double newStockBalance = getStockBalance() - totalWeight;
        LocalDate outcomeFromWarehouseDate = validateDate(date);

        if (newStockBalance < 0) {
            throw new InsufficientStockException("Insufficient stock");
        }

        outcomeGoodsRepository.save(new OutcomeGoodsEntity(boxes, totalWeight, outcomeFromWarehouseDate));
        stockBalanceRepository.save(new StockBalanceEntity(outcomeFromWarehouseDate, newStockBalance));
    }


    public void waste(double weight, String date) {
        validateWeight(weight);
        LocalDate wasteDate = validateDate(date);
        double newStockBalance = getStockBalance() - weight;

        if (newStockBalance < 0) {
            throw new InsufficientStockException("Insufficient stock");
        }

        wasteRepository.save(new WasteEntity(weight, wasteDate));
        stockBalanceRepository.save(new StockBalanceEntity(wasteDate, newStockBalance));
    }


    public double getStockBalance() {
        Double stockBalance = stockBalanceRepository.findLastStockBalance();
        if (stockBalance == null || stockBalance == 0) {
            return 0;
        } else return stockBalance;
    }

    private double validateWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        } else return weight;
    }

    private LocalDate validateDate(String date) {
        return date == null ? LocalDate.now() : dateFormatChecker.parseStringToLocalDate(date);
    }
}


