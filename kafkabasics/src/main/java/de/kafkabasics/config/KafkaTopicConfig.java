package de.kafkabasics.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Value("${kafka.topic.name}")
	private String topicName;

	@Value("${kafka.topic.replier.name}")
	private String replierTopicName;

	@Value("${kafka.topic.partitionCount}")
	private String partitionCount;

	@Bean
	public NewTopic quickStartTopic() {
		return TopicBuilder.name(topicName).partitions(Integer.valueOf(partitionCount)).replicas(1).build();
	}

	@Bean
	public NewTopic replierTopic() {
		return TopicBuilder.name(replierTopicName).partitions(Integer.valueOf(1)).replicas(1).build();
	}
}