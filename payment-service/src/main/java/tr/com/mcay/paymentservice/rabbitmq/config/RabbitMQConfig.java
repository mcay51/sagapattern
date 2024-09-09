package tr.com.mcay.paymentservice.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Queue orderQueue() {
        return new Queue("order.queue");
    }
    @Bean
    public Queue orderCancelQueue() {
        return new Queue("order-cancel.queue");
    }
    @Bean
    public Queue orderCompleteQueue() {
        return new Queue("order-complete.queue");
    }

    @Bean
    public Queue inventoryQueue() {
        return new Queue("inventory.queue");
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue("payment.queue");
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order.exchange");
    }

    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(inventoryQueue).to(orderExchange).with("inventory.routingkey");
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(paymentQueue).to(orderExchange).with("payment.routingkey");
    }
    @Bean
    public Binding orderCancelBinding(Queue orderCancelQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderCancelQueue).to(orderExchange).with("order-cancel.routingkey");
    }
    @Bean
    public Binding orderCompleteBinding(Queue orderCompleteQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderCompleteQueue).to(orderExchange).with("order-complete.routingkey");
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}


