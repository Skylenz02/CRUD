package com.bhanu.library.repository;

import com.bhanu.library.model.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
