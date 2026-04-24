package it.kevien.demo.inventoryservice.consumer;

import it.kevien.demo.inventoryservice.service.InventoryService;
import it.kevien.demo.sharedevents.model.order.OrderCreated;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final InventoryService service;

    public OrderCreatedConsumer(InventoryService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${app.kafka.orders.order-created-topic}", groupId = "${app.kafka.inventory.group-id}")
    public void consume(OrderCreated event) {
        service.processOrder(event);
    }
}
