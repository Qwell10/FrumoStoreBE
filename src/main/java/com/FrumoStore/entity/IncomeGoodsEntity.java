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
public class IncomeGoodsEntity {

    @Id
    @SequenceGenerator(
            name = "consumables_sequence",
            sequenceName = "consumables_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "consumables_sequence"
    )
    private long id;
    private double weight;
    private LocalDate date;

    public IncomeGoodsEntity(double weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }
}

