package it.kevien.demo.orderservice.service;

import it.kevien.demo.sharedevents.model.OrderCreated;
import it.kevien.demo.sharedevents.model.OrderItem;
import it.kevien.demo.orderservice.model.dto.CreateOrderRequest;
import it.kevien.demo.orderservice.model.dto.CreateOrderResponse;
import it.kevien.demo.orderservice.publisher.OrderEventPublisher;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderEventPublisher publisher;

    public OrderService(OrderEventPublisher publisher) {
        this.publisher = publisher;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();
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
        publisher.send(event);
        return new CreateOrderResponse(orderId);
    }
}
