package it.kevien.demo.sharedevents.model.inventory;

import java.math.BigDecimal;

public record StockReserved(String orderId, String customerId, String reservationId, BigDecimal totalAmount, long reservedAt) {

}
