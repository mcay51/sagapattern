package tr.com.mcay.orderservice.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private int quantity;
    private String status; // PENDING, COMPLETED, CANCELED

    public Order(String productId, int quantity, String status) {
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }
}