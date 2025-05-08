package com.bhanu.library.controller;

import com.bhanu.library.dto.BookRequest;
import com.bhanu.library.dto.BookResponse;
import com.bhanu.library.dto.GenreStatsResponse;
import com.bhanu.library.model.Book;
import com.bhanu.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

@WebFluxTest(BookController.class)
@WithMockUser
public class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookService bookService;

    @Test
    void testCreateBook_success() {
        BookRequest request = new BookRequest("Clean Code", "Robert Martin", "Programming");
        Book book = new Book("1", "Clean Code", "Programming", "author123");

        when(bookService.createBook(any())).thenReturn(Mono.just(book));

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/books")
                .header("X-Request-ID", "test-request-1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.title").isEqualTo("Clean Code")
                .jsonPath("$.genre").isEqualTo("Programming")
                .jsonPath("$.authorId").isEqualTo("author123");

    }

}
