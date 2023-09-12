package artem.strelcov.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "buyer_email")
    private String buyerEmail;
    @Column(name = "owner_email")
    private String ownerEmail;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "car_id")
    private String carId;
    @Column(name = "is_confirmed")
    private Boolean isConfirmed;
}
