package it.kevien.demo.notificationservice.consumer;

import it.kevien.demo.notificationservice.service.NotificationService;
import it.kevien.demo.sharedevents.model.inventory.StockFailed;
import it.kevien.demo.sharedevents.model.payment.PaymentConfirmed;
import it.kevien.demo.sharedevents.model.payment.PaymentFailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {

    private final NotificationService service;

    public NotificationConsumer(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${app.kafka.payment.payment-confirmed-topic}", groupId = "${app.kafka.notification.group-id}")
    public void onPaymentConfirmed(PaymentConfirmed event) {
        log.info("[NOTIFICATION] PaymentConfirmed received - orderId={}", event.orderId());
        service.sendEmail(event);
    }

    @KafkaListener(topics = "${app.kafka.payment.payment-failed-topic}", groupId = "${app.kafka.notification.group-id}")
    public void onPaymentFailed(PaymentFailed event) {
        log.warn("[NOTIFICATION] PaymentFailed received - orderId={}", event.orderId());
        service.sendEmail(event);
    }

    @KafkaListener(topics = "${app.kafka.inventory.stock-failed-topic}", groupId = "${app.kafka.notification.group-id}")
    public void onStockFailed(StockFailed event) {
        log.warn("[NOTIFICATION] StockFailed received - orderId={}", event.orderId());
        service.sendEmail(event);
    }
}
