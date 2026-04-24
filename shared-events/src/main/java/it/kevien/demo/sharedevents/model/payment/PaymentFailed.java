package it.kevien.demo.sharedevents.model.payment;

public record PaymentFailed(String orderId, String reason, long failedAt) {

}
