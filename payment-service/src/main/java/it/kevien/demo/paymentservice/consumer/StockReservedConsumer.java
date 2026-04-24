package it.kevien.demo.paymentservice.consumer;

import it.kevien.demo.paymentservice.service.PaymentService;
import it.kevien.demo.sharedevents.model.inventory.StockReserved;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockReservedConsumer {

    private final PaymentService service;

    public StockReservedConsumer(PaymentService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${app.kafka.inventory.stock-reserved-topic}", groupId = "${app.kafka.payment.group-id}")
    public void consume(StockReserved event) {
        log.info("[PAYMENT] StockReserved received - orderId={}, reservationId={}, totalAmount={}", event.orderId(), event.reservationId(), event.totalAmount());
        service.processStockReserved(event);
    }
}
