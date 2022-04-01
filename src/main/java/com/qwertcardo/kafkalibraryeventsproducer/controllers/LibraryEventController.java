package com.qwertcardo.kafkalibraryeventsproducer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qwertcardo.kafkalibraryeventsproducer.domain.LibraryEvent;
import com.qwertcardo.kafkalibraryeventsproducer.domain.enums.LibraryEventType;
import com.qwertcardo.kafkalibraryeventsproducer.producer.LibraryEventProducer;

@RestController
@RequestMapping(value = "/libraryevent")
public class LibraryEventController {
	
	@Autowired
	private LibraryEventProducer libraryEventProducer;

	@PostMapping(value = "/v1/save")
	public ResponseEntity<?> saveLibraryEvent(@RequestBody LibraryEvent libraryEvent) {
		try {
			libraryEvent.setLibraryEventType(LibraryEventType.NEW);
			libraryEventProducer.sendLibraryEventSynchronous(libraryEvent);
			return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping(value = "/v1/update")
	public ResponseEntity<?> updateLibraryEvent(@RequestBody LibraryEvent libraryEvent) {
		try {
			libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
			libraryEventProducer.sendLibraryEventV2(libraryEvent);
			return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping(value = "/v2/save")
	public ResponseEntity<?> saveLibraryEventV2(@RequestBody LibraryEvent libraryEvent) {
		try {
			libraryEvent.setLibraryEventType(LibraryEventType.NEW);
			libraryEventProducer.sendLibraryEventV2(libraryEvent);
			return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
