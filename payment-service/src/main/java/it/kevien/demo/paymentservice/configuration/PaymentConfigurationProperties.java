package it.kevien.demo.paymentservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.payment")
public record PaymentConfigurationProperties(String groupId, String paymentConfirmedTopic, String paymentFailedTopic) {

}
