package de.kafkabasics.consumer;

import java.time.LocalDateTime;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import de.kafkabasics.config.KafkaMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KafkaConsumerService {

	private final KafkaTemplate<String, KafkaMessage> replyTemplate;
	private final NewTopic replierTopic;


	@KafkaListener(topics = "${kafka.topic.name}", groupId = "anotherGroup", concurrency = "1")
	public void listenWithAnotherConsumer(@Payload KafkaMessage kafkaMessage) {
		System.out.println(String.format("Message received by another consumer. %s - %s", kafkaMessage.getTimestamp(),
				kafkaMessage.getMessage()));

		replyTemplate.send((new ProducerRecord<String, KafkaMessage>(replierTopic.name(),
				new KafkaMessage("transactional1 " + kafkaMessage.getMessage(), LocalDateTime.now()))));

		// intentionally thrown to see transaction rollback
		Integer x = null;
		x.doubleValue();

		replyTemplate.send((new ProducerRecord<String, KafkaMessage>(replierTopic.name(),
				new KafkaMessage("transactional2 " + kafkaMessage.getMessage(), LocalDateTime.now()))));
	}

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "replierGroup")
	@SendTo("${kafka.topic.replier.name}")
	public KafkaMessage listenAndReply(@Payload KafkaMessage kafkaMessage) {
		System.out.println(String.format("Message received by replier. %s - %s", kafkaMessage.getTimestamp(),
				kafkaMessage.getMessage()));
		return new KafkaMessage("Thanks for sending " + kafkaMessage.getMessage(), LocalDateTime.now());
	}

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "aGroup", concurrency = "2")
	public void listen(@Payload KafkaMessage kafkaMessage) {
		System.out.println(
				String.format("Message received. %s - %s", kafkaMessage.getTimestamp(), kafkaMessage.getMessage()));

	}

}
