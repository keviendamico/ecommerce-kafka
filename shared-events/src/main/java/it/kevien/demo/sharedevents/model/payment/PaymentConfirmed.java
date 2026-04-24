package it.kevien.demo.sharedevents.model.payment;

import java.math.BigDecimal;

public record PaymentConfirmed(String orderId, String paymentId, BigDecimal amount, long confirmedAt) {

}
