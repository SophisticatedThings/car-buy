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

    private String brand;
    private String model;
    private Integer rentPrize;
    private String driveType;
    private Integer year;
    private Integer amountOfSeats;
    private String transmission;
    private String steeringWheelPosition;
    private String classOfTheAuto;
}
