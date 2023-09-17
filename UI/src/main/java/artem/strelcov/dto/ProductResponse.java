package artem.strelcov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String brand;
    private String model;
    private Integer prize;
    private String driveType;
    private String gearBox;
    private String typeOfEngine;
    private Integer amountOfSeats;
    private String imageUrl;
    private String owner;
}
