package it.kevien.demo.orderservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.orders")
public record OrderConfigurationProperties(String orderCreatedTopic) {

}
