package it.kevien.demo.inventoryservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    private final InventoryConfigurationProperties properties;

    public KafkaTopicConfiguration(InventoryConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NewTopic stockReservedTopic() {
        return TopicBuilder.name(properties.stockReservedTopic()).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic stockFailedTopic() {
        return TopicBuilder.name(properties.stockFailedTopic()).partitions(3).replicas(1).build();
    }
}
