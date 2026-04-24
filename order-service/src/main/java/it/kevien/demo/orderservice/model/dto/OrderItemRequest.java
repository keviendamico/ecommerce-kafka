package it.kevien.demo.orderservice.model.dto;

import java.math.BigDecimal;

public record OrderItemRequest(String productId, int quantity, BigDecimal unitPrice) {

}
