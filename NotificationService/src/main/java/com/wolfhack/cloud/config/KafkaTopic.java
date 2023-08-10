package com.wolfhack.cloud.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopic {

	@Value(value = "${spring.kafka.bootstrap-servers}") private String bootstrapAddress;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic multicastMessageTopic() {
		return TopicBuilder.name("multiple-message").build();
	}

	@Bean
	public NewTopic subscriptionTopic() {
		return TopicBuilder.name("subscribe").build();
	}

	@Bean
	public NewTopic topicMessageTopic() {
		return TopicBuilder.name("single-message").build();
	}
}
