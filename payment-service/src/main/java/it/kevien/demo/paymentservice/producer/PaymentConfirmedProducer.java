package it.kevien.demo.paymentservice.producer;

import it.kevien.demo.paymentservice.configuration.PaymentConfigurationProperties;
import it.kevien.demo.sharedevents.model.payment.PaymentConfirmed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentConfirmedProducer {

    private final PaymentConfigurationProperties properties;
    private final KafkaTemplate<String, PaymentConfirmed> kafkaTemplate;

    public PaymentConfirmedProducer(PaymentConfigurationProperties properties, KafkaTemplate<String, PaymentConfirmed> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(PaymentConfirmed event) {
        kafkaTemplate.send(properties.paymentConfirmedTopic(), event.orderId(), event);
        log.info("[PAYMENT] PaymentConfirmed published - orderId={}, paymentId={}, amount={}", event.orderId(), event.paymentId(), event.amount());
    }
}
