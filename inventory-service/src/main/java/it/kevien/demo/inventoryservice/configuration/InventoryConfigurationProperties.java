package it.kevien.demo.inventoryservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.inventory")
public record InventoryConfigurationProperties(String groupId, String stockReservedTopic, String stockFailedTopic) {

}
