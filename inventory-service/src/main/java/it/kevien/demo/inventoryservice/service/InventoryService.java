package it.kevien.demo.inventoryservice.service;

import it.kevien.demo.inventoryservice.producer.StockFailedProducer;
import it.kevien.demo.inventoryservice.producer.StockReservedProducer;
import it.kevien.demo.inventoryservice.repository.InventoryRepository;
import it.kevien.demo.sharedevents.model.inventory.StockFailed;
import it.kevien.demo.sharedevents.model.inventory.StockReserved;
import it.kevien.demo.sharedevents.model.order.OrderCreated;
import it.kevien.demo.sharedevents.model.order.OrderItem;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository repository;
    private final StockReservedProducer stockReservedProducer;
    private final StockFailedProducer stockFailedProducer;

    public InventoryService(InventoryRepository repository, StockReservedProducer stockReservedProducer, StockFailedProducer stockFailedProducer) {
        this.repository = repository;
        this.stockReservedProducer = stockReservedProducer;
        this.stockFailedProducer = stockFailedProducer;
    }

    public void processOrder(OrderCreated order) {
        Map<String, Integer> inventory = repository.findAll();
        List<String> rejectedProducts = order.items()
                .stream()
                .filter(item -> {
                    Integer quantity = inventory.get(item.productId());
                    return quantity == null || quantity < item.quantity();
                })
                .map(OrderItem::productId)
                .toList();
        if (!rejectedProducts.isEmpty()) {
            stockFailedProducer.send(new StockFailed(order.orderId(), "Quantity not available for the following products: " + String.join(", ", rejectedProducts), Instant.now().toEpochMilli()));
            return;
        }
        stockReservedProducer.send(new StockReserved(order.orderId(), UUID.randomUUID().toString(), order.totalAmount(), Instant.now().toEpochMilli()));
    }
}
