package com.bhanu.library.service;

import com.bhanu.library.exception.BookNotFoundException;
import com.bhanu.library.model.Book;
import com.bhanu.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
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
                    existing.setAuthor(book.getAuthor());
                    return bookRepository.save(existing);
                });
    }

    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(id);
    }
}
