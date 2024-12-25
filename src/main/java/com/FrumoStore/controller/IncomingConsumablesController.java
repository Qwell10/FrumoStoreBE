package com.FrumoStore.controller;


import com.FrumoStore.exception.DateInvalidFormatException;
import com.FrumoStore.exception.ErrorResponse;
import com.FrumoStore.service.IncomingConsumablesService;
import dto.IncomingConsumablesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("IncomingConsumables")
public class IncomingConsumablesController {

    @Autowired
    IncomingConsumablesService incomingConsumablesService;

    @PostMapping("/newIncome")
    public ResponseEntity<String> newIncome(@RequestBody IncomingConsumablesDto incomingConsumablesDto) {
        try {
            incomingConsumablesService.incomeToWarehouse(incomingConsumablesDto.getWeight(), incomingConsumablesDto.getDate());
            return ResponseEntity.status(HttpStatus.CREATED).body("Incoming consumables was saved to Database.");
        } catch (IllegalArgumentException | DateInvalidFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ExceptionHandler(value = DateInvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateInvalidFormatException(DateInvalidFormatException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
