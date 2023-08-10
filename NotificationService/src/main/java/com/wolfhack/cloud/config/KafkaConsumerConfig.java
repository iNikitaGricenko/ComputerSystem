package com.wolfhack.cloud.config;

import com.wolfhack.cloud.model.MulticastMessagePOJO;
import com.wolfhack.cloud.model.SubscriptionPOJO;
import com.wolfhack.cloud.model.TopicMessagePOJO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}") private String bootstrapServers;

	@Bean
	public ConsumerFactory<String, MulticastMessagePOJO> multicastMessageConsumerFactory() {
		Map<String, Object> properties = getDefaultConsumerFactoryProperties();
		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(MulticastMessagePOJO.class));
	}

	@Bean
	public ConsumerFactory<String, SubscriptionPOJO> subscriptionConsumerFactory() {
		Map<String, Object> properties = getDefaultConsumerFactoryProperties();
		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(SubscriptionPOJO.class));
	}

	@Bean
	public ConsumerFactory<String, TopicMessagePOJO> topicMessageConsumerFactory() {
		Map<String, Object> properties = getDefaultConsumerFactoryProperties();
		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(TopicMessagePOJO.class));
	}

	private Map<String, Object> getDefaultConsumerFactoryProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.wolfhack.cloud.*");
		return properties;
	}

}
