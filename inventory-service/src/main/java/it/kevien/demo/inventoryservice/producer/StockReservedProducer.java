package it.kevien.demo.inventoryservice.producer;

import it.kevien.demo.inventoryservice.configuration.InventoryConfigurationProperties;
import it.kevien.demo.sharedevents.model.inventory.StockReserved;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockReservedProducer {

    private final InventoryConfigurationProperties properties;
    private final KafkaTemplate<String, StockReserved> kafkaTemplate;

    public StockReservedProducer(InventoryConfigurationProperties properties, KafkaTemplate<String, StockReserved> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(StockReserved event) {
        kafkaTemplate.send(properties.stockReservedTopic(), event.orderId(), event);
    }
}
