package com.qwertcardo.kafkalibraryeventsproducer.domain;

import com.qwertcardo.kafkalibraryeventsproducer.domain.enums.LibraryEventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEvent {

	private Integer libraryEventId;
	private LibraryEventType libraryEventType;
	private Book book;
}
