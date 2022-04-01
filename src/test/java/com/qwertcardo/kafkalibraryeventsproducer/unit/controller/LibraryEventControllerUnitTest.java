package com.qwertcardo.kafkalibraryeventsproducer.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwertcardo.kafkalibraryeventsproducer.controllers.LibraryEventController;
import com.qwertcardo.kafkalibraryeventsproducer.domain.Book;
import com.qwertcardo.kafkalibraryeventsproducer.domain.LibraryEvent;
import com.qwertcardo.kafkalibraryeventsproducer.producer.LibraryEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryEventProducer libraryEventProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void postLibraryEvent() throws Exception {
        Book book = Book.builder()
                        .id("123")
                        .author("Tokien")
                        .name("LOTR")
                        .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                                                .libraryEventId(null)
                                                .book(book)
                                                .build();


        String json = objectMapper.writeValueAsString(libraryEvent);
        mockMvc.perform(post("/libraryevent/v1/save")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postLibraryEventWithBookValid() throws Exception {
        Book book = Book.builder()
                .id("123")
                .author("Tokien")
                .name("LOTR")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();


        String json = objectMapper.writeValueAsString(libraryEvent);
        mockMvc.perform(post("/libraryevent/v1/save")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postLibraryEvent_4xx() throws Exception {
        Book book = Book.builder()
                .id("123")
                .author(null)
                .name(null)
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();


        String json = objectMapper.writeValueAsString(libraryEvent);
        String expectedContentError = "book.author - must not be blank, book.name - must not be blank";

        mockMvc.perform(post("/libraryevent/v1/save")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(expectedContentError));
    }
}
