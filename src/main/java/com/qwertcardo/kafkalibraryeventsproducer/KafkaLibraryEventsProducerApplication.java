package com.qwertcardo.kafkalibraryeventsproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class KafkaLibraryEventsProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaLibraryEventsProducerApplication.class, args);
	}

}
