package it.kevien.demo.orderservice.producer;

import it.kevien.demo.sharedevents.model.order.OrderCreated;
import it.kevien.demo.orderservice.configuration.OrderConfigurationProperties;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

    private final OrderConfigurationProperties properties;
    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;

    public OrderEventProducer(OrderConfigurationProperties properties, KafkaTemplate<String, OrderCreated> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderCreated event) {
        kafkaTemplate.send(properties.orderCreatedTopic(), event.orderId(), event);
    }
}
