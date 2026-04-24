package it.kevien.demo.sharedevents.model.inventory;


public record StockFailed(String orderId, String customerId, String reason, long failedAt) {

}
