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
public class WasteEntity {
    @Id
    @SequenceGenerator(
            name = "waste_sequence",
            sequenceName = "waste_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "waste_sequence"
    )
    private long id;
    private double weight;
    private LocalDate date;
}
