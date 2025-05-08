package com.bhanu.library.service;

import com.bhanu.library.dto.GenreStatsResponse;
import com.bhanu.library.exception.BookNotFoundException;
import com.bhanu.library.model.Book;
import com.bhanu.library.repository.AuthorRepository;
import com.bhanu.library.repository.BookRepository;
import com.bhanu.library.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookServiceTest {

    @Mock BookRepository bookRepository;
    @Mock AuthorRepository authorRepository;
    @Mock GenreRepository genreRepository;
    @Mock ReactiveRedisTemplate<String, Book> redisTemplate;
    @Mock ReactiveValueOperations<String, Book> valueOperations;

    @InjectMocks
    BookService bookService;

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testGetBookById_foundInCache() {
        Book mockBook = new Book("1", "Test Book", "Fiction", "author1");

        when(valueOperations.get("book::1")).thenReturn(Mono.just(mockBook));
        // 🔒 Add this even if not expected to be used
        when(bookRepository.findById("1")).thenReturn(Mono.just(mockBook));

        StepVerifier.create(bookService.getBookById("1"))
                .expectNext(mockBook)
                .verifyComplete();
    }


    @Test
    void testGetBookById_notInCache_foundInDB() {
        Book mockBook = new Book("1", "Test Book", "Fiction", "author1");

        when(valueOperations.get("book::1")).thenReturn(Mono.empty());
        when(bookRepository.findById("1")).thenReturn(Mono.just(mockBook));
        when(valueOperations.set(eq("book::1"), eq(mockBook), any(Duration.class))).thenReturn(Mono.just(true));

        StepVerifier.create(bookService.getBookById("1"))
                .expectNext(mockBook)
                .verifyComplete();
    }

    @Test
    void testGetBookById_notFoundAnywhere() {
        when(valueOperations.get("book::2")).thenReturn(Mono.empty());
        when(bookRepository.findById("2")).thenReturn(Mono.empty());

        StepVerifier.create(bookService.getBookById("2"))
                .expectError(BookNotFoundException.class)
                .verify();
    }

    @Test
    void testGetGenreStats() {
        GenreStatsResponse stats = new GenreStatsResponse("Fantasy", 5);
        when(bookRepository.getGenreStats()).thenReturn(Flux.just(stats));

        StepVerifier.create(bookService.getGenreStatistics())
                .expectNext(stats)
                .verifyComplete();
    }
}
