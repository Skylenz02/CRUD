package com.bhanu.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
    private String id;
    private String title;
    private String genre;
    private String authorName;
}
