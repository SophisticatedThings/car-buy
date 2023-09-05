package artem.strelcov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("product")
public class Product {
    @Id
    private String id;
    @Field(name = "owner")
    private String owner;
    @Field(name = "brand")
    private String brand;
    @Field(name = "model")
    @Indexed(unique = true)
    private String model;
    @Field(name = "prize")
    private Integer prize;
    @Field(name = "driveType")
    private String driveType;
    @Field(name = "gearBox")
    private String gearBox;
    @Field(name = "typeOfEngine")
    private String typeOfEngine;
    @Field(name = "amountOfSeats")
    private Integer amountOfSeats;
    @Field(name = "imageUrl")
    private String imageUrl;
}
