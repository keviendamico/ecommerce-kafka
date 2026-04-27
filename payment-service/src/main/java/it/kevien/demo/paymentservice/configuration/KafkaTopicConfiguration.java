package it.kevien.demo.paymentservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    private final PaymentConfigurationProperties properties;

    public KafkaTopicConfiguration(PaymentConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NewTopic paymentConfirmedTopic() {
        return TopicBuilder.name(properties.paymentConfirmedTopic()).partitions(3).replicas(3).build();
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder.name(properties.paymentFailedTopic()).partitions(3).replicas(3).build();
    }
}
