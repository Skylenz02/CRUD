package com.bhanu.library.controller;

import com.bhanu.library.dto.BookRequest;
import com.bhanu.library.dto.BookResponse;
import com.bhanu.library.dto.GenreStatsResponse;
import com.bhanu.library.model.Book;
import com.bhanu.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Mono<Book> createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @GetMapping
    public Flux<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }
    @GetMapping("/authors/name/{authorName}")
    public Flux<BookResponse> getBooksByAuthorName(@PathVariable String authorName) {
        return bookService.getBooksByAuthorName(authorName);
    }

    @GetMapping("/genre/{genre}")
    public Flux<BookResponse> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }


    @GetMapping("/{id}")
    public Mono<Book> getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Serializable>>> deleteBook(@PathVariable String id) {
        return bookService.deleteBook(id)
                .thenReturn(
                        ResponseEntity.ok(Map.of(
                                "message", "Book deleted successfully",
                                "timestamp", LocalDateTime.now()
                        ))
                );
    }

    @GetMapping("/stats/genre")
    public Flux<GenreStatsResponse> getGenreStats() {
        return bookService.getGenreStatistics();
    }

}
