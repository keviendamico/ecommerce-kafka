package it.kevien.demo.sharedevents.model.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreated(String orderId, String customerId, List<OrderItem> items, BigDecimal totalAmount, long createdAt) {

}
