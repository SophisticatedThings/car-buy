package artem.strelcov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    private String id;

    @Field(name = "brand")
    private String brand;

    @Field(name = "model")
    @Indexed(unique = true)
    private String model;

    @Field(name = "rentPrize")
    private Integer rentPrize;

    @Field(name = "driveType")
    private String driveType;

    @Field(name = "year")
    private Integer year;

    @Field(name = "amountOfSeats")
    private Integer amountOfSeats;

    @Field(name = "transmission")
    private String transmission;

    @Field(name = "steeringWheelPosition")
    private String steeringWheelPosition;

    @Field(name = "classOfTheAuto")
    private String classOfTheAuto;




}
