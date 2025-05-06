package com.bhanu.library.service;

import com.bhanu.library.dto.BookRequest;
import com.bhanu.library.dto.BookResponse;
import com.bhanu.library.dto.GenreStatsResponse;
import com.bhanu.library.exception.BookNotFoundException;
import com.bhanu.library.model.Author;
import com.bhanu.library.model.Book;
import com.bhanu.library.model.Genre;
import com.bhanu.library.repository.AuthorRepository;
import com.bhanu.library.repository.BookRepository;
import com.bhanu.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public Mono<Book> createBook(BookRequest bookRequest) {
        return authorRepository.findByName(bookRequest.getAuthorName())
                .switchIfEmpty(
                        authorRepository.save(new Author(null, bookRequest.getAuthorName()))
                )
                .flatMap(author ->
                        genreRepository.findByName(bookRequest.getGenre())
                                .switchIfEmpty(
                                        genreRepository.save(new Genre(null, bookRequest.getGenre()))
                                )
                                .flatMap(genre -> {
                                    Book book = new Book();
                                    book.setTitle(bookRequest.getTitle());
                                    book.setGenre(genre.getName());
                                    book.setAuthorId(author.getId());
                                    return bookRepository.save(book);
                                })
                );
    }

    public Mono<BookResponse> mapToBookResponse(Book book) {
        return authorRepository.findById(book.getAuthorId())
                .map(author -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        author.getName()
                ));
    }

    public Flux<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .flatMap(this::mapToBookResponse);
    }

    public Flux<BookResponse> getBooksByAuthorName(String authorName) {
        return authorRepository.findByName(authorName)
                .doOnNext(author -> System.out.println("Author ID found: " + author.getId()))
                .flatMapMany(author -> bookRepository.findByAuthorId(author.getId()))
                .flatMap(this::mapToBookResponse);
    }

    public Flux<BookResponse> getBooksByGenre(String genreName) {
        return bookRepository.findByGenre(genreName)
                .flatMap(this::mapToBookResponse);
    }

    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException(id)));
    }

    public Mono<Book> updateBook(String id, Book book) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthorId(book.getAuthorId());
                    existing.setGenre(book.getGenre());
                    return bookRepository.save(existing);
                });
    }

    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(id);
    }

    public Flux<GenreStatsResponse> getGenreStatistics() {
        return bookRepository.getGenreStats();
    }
}
