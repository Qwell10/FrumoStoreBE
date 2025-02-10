package com.FrumoStore.controller;


import com.FrumoStore.exception.DateInvalidFormatException;
import com.FrumoStore.exception.ErrorResponse;
import com.FrumoStore.exception.InsufficientStockException;
import com.FrumoStore.service.GoodsService;
import com.FrumoStore.dto.IncomeGoodsDto;
import com.FrumoStore.dto.OutcomeGoodsDto;
import com.FrumoStore.dto.StockBalanceDto;
import com.FrumoStore.dto.WasteDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @PostMapping("/newIncome")
    @Transactional
    public ResponseEntity<String> newIncome(@RequestBody IncomeGoodsDto incomeGoodsDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false); //false  = if session exists -> get her; if not don't create new one

        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

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

    @GetMapping("/getActualStockBalance")
    public StockBalanceDto getActualStockBalance() {
        return new StockBalanceDto(goodsService.getStockBalance());
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
