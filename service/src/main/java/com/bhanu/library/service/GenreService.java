package com.bhanu.library.service;

import com.bhanu.library.model.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreService {
    Mono<Genre> createGenre(Genre genre);
    Flux<Genre> getAllGenres();
    Mono<Genre> getGenreById(String id);
    Mono<Genre> updateGenre(String id, Genre genre);
    Mono<Void> deleteGenre(String id);
}
