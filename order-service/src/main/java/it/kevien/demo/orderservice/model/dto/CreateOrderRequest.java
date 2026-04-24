package it.kevien.demo.orderservice.model.dto;

import java.util.List;

public record CreateOrderRequest(String customerId, List<OrderItemRequest> items) {

}
