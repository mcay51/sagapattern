package tr.com.mcay.orderservice.order.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class OrderDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 43L;
    private Long id;
    private String productId;
    private int quantity;

    // Parametresiz constructor
    public OrderDto() {
    }

    // Parametreli constructor
    public OrderDto(Long id, String productId, int quantity) {
        this.id=id;
        this.productId = productId;
        this.quantity = quantity;
    }

}
