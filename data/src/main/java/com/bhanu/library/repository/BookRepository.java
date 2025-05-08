package com.bhanu.library.repository;

import com.bhanu.library.dto.GenreStatsResponse;
import com.bhanu.library.model.Book;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByGenre(String genre);
    Flux<Book> findByAuthorId(String authorId);
    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$genre', 'count': { '$sum': 1 } } }",
            "{ '$project': { 'genre': '$_id', 'count': 1, '_id': 0 } }"
    })
    Flux<GenreStatsResponse> getGenreStats();
}
