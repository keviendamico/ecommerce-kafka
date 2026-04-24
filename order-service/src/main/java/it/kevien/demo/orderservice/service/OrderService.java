package it.kevien.demo.orderservice.service;

import it.kevien.demo.sharedevents.model.order.OrderCreated;
import it.kevien.demo.sharedevents.model.order.OrderItem;
import it.kevien.demo.orderservice.model.dto.CreateOrderRequest;
import it.kevien.demo.orderservice.model.dto.CreateOrderResponse;
import it.kevien.demo.orderservice.producer.OrderEventProducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderEventProducer producer;

    public OrderService(OrderEventProducer producer) {
        this.producer = producer;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        log.info("[ORDER] Creating order - orderId={}, customerId={}, items={}", orderId, request.customerId(), request.items().size());
        BigDecimal totalAmount = request.items()
                .stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderCreated event = new OrderCreated(
                orderId,
                request.customerId(),
                request.items().stream().map(i -> new OrderItem(i.productId(), i.quantity(), i.unitPrice())).toList(),
                totalAmount,
                Instant.now().toEpochMilli()
        );
        producer.send(event);
        return new CreateOrderResponse(orderId);
    }
}
