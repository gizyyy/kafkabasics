package de.kafkabasics.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KafkaBaseConfig {

	private final KafkaAdmin admin;

	@Bean
	public ProducerFactory<String, KafkaMessage> producerFactory() {
		HashMap<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				admin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configMap.put(ProducerConfig.ACKS_CONFIG, "all");
		configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		return new DefaultKafkaProducerFactory<String, KafkaMessage>(configMap);
	}

	@Bean
	public ProducerFactory<String, KafkaMessage> producerForReplierFactory() {
		HashMap<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				admin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configMap.put(ProducerConfig.ACKS_CONFIG, "all");
		configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		configMap.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "trasactionId");
		return new DefaultKafkaProducerFactory<String, KafkaMessage>(configMap);
	}

	@Bean
	public ConsumerFactory<String, KafkaMessage> consumerFactory() {
		Map<String, Object> configMap = new HashMap<String, Object>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				admin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
		configMap.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaMessage.class);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, KafkaMessage>(configMap);
	}
	

}
