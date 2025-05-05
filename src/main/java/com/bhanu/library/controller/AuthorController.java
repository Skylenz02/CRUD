package com.bhanu.library.controller;

import com.bhanu.library.model.Author;
import com.bhanu.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public Mono<Author> createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @GetMapping
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthorById(@PathVariable String id) {
        return authorRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Author> updateAuthor(@PathVariable String id, @RequestBody Author updatedAuthor) {
        return authorRepository.findById(id)
                .flatMap(existingAuthor -> {
                    existingAuthor.setName(updatedAuthor.getName());
                    existingAuthor.setBio(updatedAuthor.getBio());
                    return authorRepository.save(existingAuthor);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable String id) {
        return authorRepository.deleteById(id);
    }
}
