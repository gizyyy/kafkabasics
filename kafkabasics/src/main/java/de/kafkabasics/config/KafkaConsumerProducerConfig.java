package de.kafkabasics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KafkaConsumerProducerConfig {

	private final KafkaTemplate<String, KafkaMessage> replyTemplate;
	private final ProducerFactory<String, KafkaMessage> producerForReplierFactory;
	private final ConsumerFactory<String, KafkaMessage> consumerFactory;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setReplyTemplate(replyTemplate);
		factory.getContainerProperties().setTransactionManager(kafkaTransactionManager());
		return factory;
	}

	@Bean
	public KafkaTransactionManager<String, KafkaMessage> kafkaTransactionManager() {
		return new KafkaTransactionManager<String, KafkaMessage>(producerForReplierFactory);
	}

}
