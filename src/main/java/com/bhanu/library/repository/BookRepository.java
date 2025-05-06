package com.bhanu.library.repository;

import com.bhanu.library.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByGenre(String genre);
    Flux<Book> findByAuthorId(String authorId);

}
