package com.FrumoStore.controller;


import com.FrumoStore.exception.DateInvalidFormatException;
import com.FrumoStore.exception.ErrorResponse;
import com.FrumoStore.exception.InsufficientStockException;
import com.FrumoStore.service.GoodsService;
import dto.IncomeGoodsDto;
import dto.OutcomeGoodsDto;
import dto.WasteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @PostMapping("/newIncome")
    @Transactional
    public ResponseEntity<String> newIncome(@RequestBody IncomeGoodsDto incomeGoodsDto) {
        goodsService.incomeToWarehouse(incomeGoodsDto.getWeight(), incomeGoodsDto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED).body("Incoming goods and stock balance was updated");
    }

    @PostMapping("/outcome")
    @Transactional
    public ResponseEntity<String> outcomeGoods(@RequestBody OutcomeGoodsDto outcomeGoodsDto) {
        goodsService.outcomeFromWarehouse(outcomeGoodsDto.getBoxes(), outcomeGoodsDto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED).body("Outcoming goods and stock balance was updated.");
    }

    @PostMapping("/waste")
    @Transactional
    public ResponseEntity<String> waste(@RequestBody WasteDto wasteDto) {
        goodsService.waste(wasteDto.getWeight(), wasteDto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED).body("Waste and stock balance was updated.");
    }



    @ExceptionHandler(value = DateInvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateInvalidFormatException(DateInvalidFormatException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = InsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInsufficientStockException(InsufficientStockException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }
}
