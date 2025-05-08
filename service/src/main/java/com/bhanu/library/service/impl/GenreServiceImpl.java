package com.bhanu.library.service.impl;

import com.bhanu.library.model.Genre;
import com.bhanu.library.repository.GenreRepository;
import com.bhanu.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Mono<Genre> createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Flux<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Mono<Genre> updateGenre(String id, Genre genre) {
        return genreRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(genre.getName());
                    return genreRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteGenre(String id) {
        return genreRepository.findById(id)
                .flatMap(existing -> genreRepository.delete(existing));
    }
}
