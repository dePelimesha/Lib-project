package com.karengin.libproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private String description;
    private String author;
    private List<String> genres;
}
