package it.kevien.demo.paymentservice.producer;

import it.kevien.demo.paymentservice.configuration.PaymentConfigurationProperties;
import it.kevien.demo.sharedevents.model.payment.PaymentFailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentFailedProducer {

    private final PaymentConfigurationProperties properties;
    private final KafkaTemplate<String, PaymentFailed> kafkaTemplate;

    public PaymentFailedProducer(PaymentConfigurationProperties properties, KafkaTemplate<String, PaymentFailed> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(PaymentFailed event) {
        kafkaTemplate.send(properties.paymentFailedTopic(), event.orderId(), event);
        log.warn("[PAYMENT] PaymentFailed published - orderId={}, reason={}", event.orderId(), event.reason());
    }
}
