package de.kafkabasics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KafkaTemplateConfig {

	private final ProducerFactory<String, KafkaMessage> producerFactory;
	private final ProducerFactory<String, KafkaMessage> producerForReplierFactory;

	@Bean
	public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
		return new KafkaTemplate<String, KafkaMessage>(producerFactory);
	}

	@Bean(name = "replyTemplate")
	public KafkaTemplate<String, KafkaMessage> replyTemplate() {
		KafkaTemplate<String, KafkaMessage> kafkaTemplate = new KafkaTemplate<String, KafkaMessage>(
				producerForReplierFactory);
		kafkaTemplate.setTransactionIdPrefix("tx-");
		return kafkaTemplate;
	}

}
