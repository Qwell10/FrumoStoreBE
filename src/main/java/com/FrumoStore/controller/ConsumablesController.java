package com.FrumoStore.controller;


import com.FrumoStore.exception.DateInvalidFormatException;
import com.FrumoStore.exception.ErrorResponse;
import com.FrumoStore.service.ConsumablesService;
import dto.IncomingConsumablesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Consumables")
public class ConsumablesController {

    @Autowired
    ConsumablesService consumablesService;

    @PostMapping("/newIncome")
    public ResponseEntity<String> newIncome(@RequestBody IncomingConsumablesDto incomingConsumablesDto) {
        consumablesService.incomeToWarehouse(incomingConsumablesDto.getWeight(), incomingConsumablesDto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED).body("Incoming consumables was saved to Database.");
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
}
