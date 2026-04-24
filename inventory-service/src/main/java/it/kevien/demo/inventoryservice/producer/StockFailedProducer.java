package it.kevien.demo.inventoryservice.producer;

import it.kevien.demo.inventoryservice.configuration.InventoryConfigurationProperties;
import it.kevien.demo.sharedevents.model.inventory.StockFailed;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockFailedProducer {

    private final InventoryConfigurationProperties properties;
    private final KafkaTemplate<String, StockFailed> kafkaTemplate;

    public StockFailedProducer(InventoryConfigurationProperties properties, KafkaTemplate<String, StockFailed> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(StockFailed event) {
        kafkaTemplate.send(properties.stockFailedTopic(), event.orderId(), event);
    }
}
