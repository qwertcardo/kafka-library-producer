package com.qwertcardo.kafkalibraryeventsproducer.producer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwertcardo.kafkalibraryeventsproducer.domain.LibraryEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventProducer {
	
	private final String LIBRARY_EVENTS_TOPIC = "library-events";

	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public SendResult<Integer, String> sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws Exception {
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		
		SendResult<Integer, String> result = null;
		try {
			result = this.kafkaTemplate.send(LIBRARY_EVENTS_TOPIC, key, value).get(1, TimeUnit.SECONDS);
			log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
		} catch (InterruptedException | ExecutionException ex) {
			log.error("Error Sending the Message and the exception is {} ", ex.getMessage());
			throw ex;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		
		ListenableFuture<SendResult<Integer, String>> listenableFuture = this.kafkaTemplate.send(LIBRARY_EVENTS_TOPIC, key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			
			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSucces(key, value, result);
			}

			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}
		});
	}
	
	public void sendLibraryEventV2(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		
		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, LIBRARY_EVENTS_TOPIC);
		
		ListenableFuture<SendResult<Integer, String>> listenableFuture = this.kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			
			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSucces(key, value, result);
			}

			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}
		});
	}

	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
		List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
		
		return new ProducerRecord<Integer, String>(topic, null, key, value, recordHeaders);
	}

	private void handleSucces(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
	}
	
	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error Sending the Message and the exception is {} ", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error on failure: {}", throwable.getMessage());
		}
	}
}
