package com.bhanu.library.service;

import com.bhanu.library.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> createAuthor(Author author);
    Flux<Author> getAllAuthors();
    Mono<Author> getAuthorById(String id);
    Mono<Author> updateAuthor(String id, Author updatedAuthor);
    Mono<Void> deleteAuthor(String id);
}
