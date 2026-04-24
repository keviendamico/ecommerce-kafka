package it.kevien.demo.orderservice.publisher;

import it.kevien.demo.sharedevents.model.OrderCreated;
import it.kevien.demo.orderservice.configuration.OrderConfigurationProperties;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final OrderConfigurationProperties properties;
    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;

    public OrderEventPublisher(OrderConfigurationProperties properties, KafkaTemplate<String, OrderCreated> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderCreated event) {
        kafkaTemplate.send(properties.orderCreatedTopic(), event.orderId(), event);
    }
}
