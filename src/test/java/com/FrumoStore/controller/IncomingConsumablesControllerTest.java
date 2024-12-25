package com.FrumoStore.controller;

import com.FrumoStore.service.IncomingConsumablesService;
import dto.IncomingConsumablesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IncomingConsumablesControllerTest {

    @Mock
    IncomingConsumablesService incomingConsumablesService;

    @InjectMocks
    IncomingConsumablesController incomingConsumablesController;

    @Test
    public void newIncome_happyScenario() {
        IncomingConsumablesDto incomingConsumablesDto = new IncomingConsumablesDto(800.25, "01-01-2025");

        ResponseEntity<String> response = incomingConsumablesController.newIncome(incomingConsumablesDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Incoming consumables was saved to Database.", response.getBody());
        verify(incomingConsumablesService, times(1)).incomeToWarehouse(incomingConsumablesDto.getWeight(), incomingConsumablesDto.getDate());
    }

    @Test
    public void newIncome_catchIllegalArgumentException() {
        IncomingConsumablesDto incomingConsumablesDto = new IncomingConsumablesDto(-100, "01-01-2025");

        ResponseEntity<String> response = incomingConsumablesController.newIncome(incomingConsumablesDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Weight cannot be negative.", response.getBody());
    }

    @Test
    public void newIncome_catchDateInvalidFormatException() {
        IncomingConsumablesDto incomingConsumablesDto = new IncomingConsumablesDto(1000, "1.1.2025");

        ResponseEntity<String> response = incomingConsumablesController.newIncome(incomingConsumablesDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

