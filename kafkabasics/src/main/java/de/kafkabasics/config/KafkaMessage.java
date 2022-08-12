package de.kafkabasics.config;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class KafkaMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private LocalDateTime timestamp;
}
