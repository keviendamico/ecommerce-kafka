package it.kevien.demo.sharedevents.model;

import java.math.BigDecimal;

public record OrderItem(String productId, int quantity, BigDecimal unitPrice) {

}
