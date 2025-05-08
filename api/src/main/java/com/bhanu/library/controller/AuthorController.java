package com.bhanu.library.controller;

import com.bhanu.library.model.Author;
import com.bhanu.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public Mono<Author> createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @GetMapping
    public Flux<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthorById(@PathVariable String id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/{id}")
    public Mono<Author> updateAuthor(@PathVariable String id, @RequestBody Author updatedAuthor) {
        return authorService.updateAuthor(id, updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable String id) {
        return authorService.deleteAuthor(id);
    }
}
