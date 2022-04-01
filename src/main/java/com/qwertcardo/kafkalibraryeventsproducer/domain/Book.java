package com.qwertcardo.kafkalibraryeventsproducer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {

	@Id
	private String id;
	
	@Field(name = "name")
	private String name;
	
	@Field(name = "author")
	private String author;
}
