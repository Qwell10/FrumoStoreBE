package com.FrumoStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockBalanceEntity {

    @Id
    @SequenceGenerator(
            name = "stockBalance_sequence",
            sequenceName = "stockBalance_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stockBalance_sequence"
    )
    private long id;
    private LocalDate receiptDate;
    private Double stockBalance;

    public StockBalanceEntity(LocalDate receiptDate, Double stockBalance) {
        this.receiptDate = receiptDate;
        this.stockBalance = stockBalance;
    }
}


