package com.wolfhack.cloud.config;

import com.wolfhack.cloud.model.MulticastMessagePOJO;
import com.wolfhack.cloud.model.SubscriptionPOJO;
import com.wolfhack.cloud.model.TopicMessagePOJO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaListenerConfig {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, MulticastMessagePOJO> multicastMessageKafkaListenerContainerFactory(
			ConsumerFactory<String, MulticastMessagePOJO> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, MulticastMessagePOJO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, SubscriptionPOJO> subscriptionKafkaListenerContainerFactory(
			ConsumerFactory<String, SubscriptionPOJO> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, SubscriptionPOJO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, TopicMessagePOJO> topicMessageKafkaListenerContainerFactory(
			ConsumerFactory<String, TopicMessagePOJO> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, TopicMessagePOJO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

}
