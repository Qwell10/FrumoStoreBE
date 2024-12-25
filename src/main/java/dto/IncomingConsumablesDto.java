package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingConsumablesDto {
    private double weight;
    private String date;

    public IncomingConsumablesDto(double weight, String date) {
        this.weight = weight;
        this.date = date;
    }
}
