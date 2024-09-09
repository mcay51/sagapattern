package tr.com.mcay.orderservice.order.entity.dto;

import java.io.Serializable;

public class OrderRequest implements Serializable {
    private String productId;
    private int quantity;

    // Parametresiz constructor
    public OrderRequest() {
    }

    // Parametreli constructor
    public OrderRequest(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter ve Setter'lar
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

