package it.kevien.demo.inventoryservice.consumer;

import it.kevien.demo.inventoryservice.service.InventoryService;
import it.kevien.demo.sharedevents.model.order.OrderCreated;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreatedConsumer {

    private final InventoryService service;

    public OrderCreatedConsumer(InventoryService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${app.kafka.orders.order-created-topic}", groupId = "${app.kafka.inventory.group-id}")
    public void consume(OrderCreated event) {
        log.info("[INVENTORY] OrderCreated received - orderId={}, items={}", event.orderId(), event.items().size());
        service.processOrder(event);
    }
}
