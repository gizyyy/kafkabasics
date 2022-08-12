package de.kafkabasics.producer;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import de.kafkabasics.config.KafkaMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

	private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
	private final NewTopic quickStartTopic;
	private AtomicInteger counter = new AtomicInteger();

	@Scheduled(fixedRate = 20000L)
	public void produceKafkaMessage() {

		ListenableFuture<SendResult<String, KafkaMessage>> send = kafkaTemplate
				.send(new ProducerRecord<String, KafkaMessage>(quickStartTopic.name(),
						"courier" + counter.getAndIncrement() % quickStartTopic.numPartitions(),
						new KafkaMessage(UUID.randomUUID().toString(), LocalDateTime.now())));

		send.addCallback(new SuccessCallback<Object>() {
			@Override
			public void onSuccess(Object result) {
				System.out.println(result);

			}
		}, new FailureCallback() {
			@Override
			public void onFailure(Throwable ex) {
			}
		});
	}
}
