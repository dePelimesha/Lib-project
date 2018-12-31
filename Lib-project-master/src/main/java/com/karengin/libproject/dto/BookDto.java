package com.karengin.libproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private String description;
    private String author;
}
