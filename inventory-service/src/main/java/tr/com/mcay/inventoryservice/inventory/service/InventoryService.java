package tr.com.mcay.inventoryservice.inventory.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.mcay.orderservice.order.dto.OrderDto;
import tr.com.mcay.inventoryservice.inventory.entity.Inventory;
import tr.com.mcay.inventoryservice.inventory.repository.InventoryRepository;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, RabbitTemplate rabbitTemplate){
        this.inventoryRepository=inventoryRepository;

        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "inventory.queue")
    public void handleOrder(OrderDto order) {
        // Stok kontrolü yap
        boolean isStockAvailable = checkStock(order.getProductId(), order.getQuantity());
System.out.println("Stok Durumu : "+isStockAvailable);
        if (isStockAvailable) {
            // Stok yeterliyse ödeme işlemini başlat
            rabbitTemplate.convertAndSend("inventory.exchange", "payment.routingkey", order);
        } else {
            // Stok yetersizse siparişi iptal et
            rabbitTemplate.convertAndSend("order.exchange", "order-cancel.routingkey", order);
        }
    }

    private boolean checkStock(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        return inventory != null && inventory.getAvailableQuantity() >= quantity;
    }

}
