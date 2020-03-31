package com.demo.KafkaStreams;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Log4j2
@SpringBootApplication
public class KafkaStreamsApplication {
	public static void main(String[] args){
		KafkaProducerConfig pC = new KafkaProducerConfig();
		KafkaConsumerConfig cC = new KafkaConsumerConfig();
		cC.consume();
		//pC.produce();
	}
}
