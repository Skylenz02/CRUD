package com.bhanu.library.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    private String name;
    private String bio;
}
