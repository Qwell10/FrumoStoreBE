package com.FrumoStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutcomeGoodsEntity {

    @Id
    @SequenceGenerator(
            name = "delivery_sequence",
            sequenceName = "delivery_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "delivery_sequence"
    )
    private long id;
    private long boxes;
    private double totalWeight;
    private LocalDate date;

    public OutcomeGoodsEntity(int boxes, double totalWeight, LocalDate date) {
        this.boxes = boxes;
        this.totalWeight = totalWeight;
        this.date = date;
    }
}
