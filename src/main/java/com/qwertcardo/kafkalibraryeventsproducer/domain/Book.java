package com.qwertcardo.kafkalibraryeventsproducer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {

	@Id
	@NotNull
	private String id;

	@Field(name = "name")
	@NotBlank
	private String name;

	@Field(name = "author")
	@NotBlank
	private String author;
}
