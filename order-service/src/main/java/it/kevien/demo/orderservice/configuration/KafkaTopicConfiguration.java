package it.kevien.demo.orderservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    private final OrderConfigurationProperties properties;

    public KafkaTopicConfiguration(OrderConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(properties.orderCreatedTopic()).partitions(3).replicas(1).build();
    }
}
