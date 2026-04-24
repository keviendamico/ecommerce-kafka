package it.kevien.demo.inventoryservice.producer;

import it.kevien.demo.inventoryservice.configuration.InventoryConfigurationProperties;
import it.kevien.demo.sharedevents.model.inventory.StockReserved;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
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
        log.info("[INVENTORY] StockReserved published - orderId={}, reservationId={}", event.orderId(), event.reservationId());
    }
}
