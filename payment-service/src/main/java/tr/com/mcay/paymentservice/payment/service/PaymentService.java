package tr.com.mcay.paymentservice.payment.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.mcay.orderservice.order.entity.dto.OrderDto;

@Service
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public PaymentService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }
    @RabbitListener(queues = "payment.queue")
    public void handleOrder(OrderDto order) {
        // Ödeme işlemini yap
        boolean isPaymentSuccessful = processPayment(order);
System.out.println("Odeme Islemi : "+isPaymentSuccessful);
        if (isPaymentSuccessful) {
            // Ödeme başarılıysa siparişi tamamla
            rabbitTemplate.convertAndSend("order.exchange", "order-complete.routingkey", order);
        } else {
            // Ödeme başarısızsa siparişi iptal et
            rabbitTemplate.convertAndSend("order.exchange", "order-cancel.routingkey", order);
        }
    }

    private boolean processPayment(OrderDto order) {
        // Gerçek bir ödeme işlemi burada yapılmalı
        return true; // Ödeme başarılı varsayalım
    }
}

