package com.bhanu.library.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String genre;
    private String authorName;
}
