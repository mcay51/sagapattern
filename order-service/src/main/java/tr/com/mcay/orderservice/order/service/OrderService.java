package tr.com.mcay.orderservice.order.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.mcay.orderservice.order.dto.OrderDto;
import tr.com.mcay.orderservice.order.entity.Order;
import tr.com.mcay.orderservice.order.entity.dto.OrderRequest;
import tr.com.mcay.orderservice.order.repository.OrderRepository;

@Service
public class OrderService {


    private final OrderRepository orderRepository;

    private final RabbitTemplate rabbitTemplate;

    public OrderService(RabbitTemplate rabbitTemplate,OrderRepository orderRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderRepository=orderRepository;
    }

    public Order createOrder(OrderRequest orderRequest) {
        // Sipariş oluşturma
        Order order = new Order(orderRequest.getProductId(), orderRequest.getQuantity(), "PENDING");
        orderRepository.save(order);

        // Stok kontrolü için mesaj gönder
        rabbitTemplate.convertAndSend("order.exchange", "inventory.routingkey", convertToDTO(order));

        return order;
    }
    public OrderDto convertToDTO(Order order) {
        OrderDto dto = new OrderDto();
        dto.setProductId(order.getProductId());
       dto.setQuantity(order.getQuantity());
       dto.setId(order.getId());
        return dto;
    }
    @RabbitListener(queues = "order-cancel.queue")
    public void cancelOrder(OrderDto orderDto) {
        Order order=new Order();
        order.setStatus("CANCELLED");
        order.setProductId(orderDto.getProductId());
        order.setQuantity(orderDto.getQuantity());
        order.setId(orderDto.getId());
        orderRepository.save(order);
    }

    @RabbitListener(queues = "order-complete.queue")
    public void completeOrder(OrderDto orderDto) {
        Order order=new Order();
        order.setStatus("COMPLETED");
        order.setProductId(orderDto.getProductId());
        order.setQuantity(orderDto.getQuantity());
        order.setId(orderDto.getId());
        orderRepository.save(order);
    }
}
