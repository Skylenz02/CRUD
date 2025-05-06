package com.bhanu.library.controller;

import com.bhanu.library.model.Genre;
import com.bhanu.library.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @PostMapping
    public Mono<ResponseEntity<Genre>> createGenre(@RequestBody Genre genre) {
        return genreRepository.save(genre)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Genre>> getGenreById(@PathVariable String id) {
        return genreRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Genre>> updateGenre(@PathVariable String id, @RequestBody Genre genre) {
        return genreRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(genre.getName());
                    return genreRepository.save(existing);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGenre(@PathVariable String id) {
        return genreRepository.findById(id)
                .flatMap(existing ->
                        genreRepository.delete(existing).then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
