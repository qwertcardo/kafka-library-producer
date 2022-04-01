package com.qwertcardo.kafkalibraryeventsproducer.domain;

import com.qwertcardo.kafkalibraryeventsproducer.domain.enums.LibraryEventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEvent {

	private Integer libraryEventId;
	private LibraryEventType libraryEventType;

	@NotNull
	@Valid
	private Book book;
}
