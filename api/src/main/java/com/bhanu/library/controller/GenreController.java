package com.bhanu.library.controller;

import com.bhanu.library.model.Genre;
import com.bhanu.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public Mono<ResponseEntity<Genre>> createGenre(@RequestBody Genre genre) {
        return genreService.createGenre(genre).map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Genre>> getGenreById(@PathVariable String id) {
        return genreService.getGenreById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Genre>> updateGenre(@PathVariable String id, @RequestBody Genre genre) {
        return genreService.updateGenre(id, genre)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGenre(@PathVariable String id) {
        return genreService.deleteGenre(id)
                .thenReturn(ResponseEntity.ok().<Void>build());
    }
}
