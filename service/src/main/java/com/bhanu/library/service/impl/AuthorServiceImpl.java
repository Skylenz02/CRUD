package com.bhanu.library.service.impl;

import com.bhanu.library.model.Author;
import com.bhanu.library.repository.AuthorRepository;
import com.bhanu.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Mono<Author> createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> updateAuthor(String id, Author updatedAuthor) {
        return authorRepository.findById(id)
                .flatMap(existingAuthor -> {
                    existingAuthor.setName(updatedAuthor.getName());
                    return authorRepository.save(existingAuthor);
                });
    }

    @Override
    public Mono<Void> deleteAuthor(String id) {
        return authorRepository.deleteById(id);
    }
}
