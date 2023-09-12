package artem.strelcov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer orderId;
    private String buyerEmail;
    private String ownerEmail;
    private LocalDateTime date;
    private String carId;
    private String token;

}
