package it.kevien.demo.sharedevents.model.payment;

public record PaymentFailed(String orderId, String customerId, String reason, long failedAt) {

}
