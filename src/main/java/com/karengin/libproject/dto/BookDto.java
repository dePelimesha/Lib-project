package com.karengin.libproject.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BookDto extends AbstractDto {
    private String title;
    private String description;
    private String author;
    private List<String> genres = new ArrayList<>();
}
