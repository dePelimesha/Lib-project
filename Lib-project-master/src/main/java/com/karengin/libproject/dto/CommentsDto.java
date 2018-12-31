package com.karengin.libproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsDto {
    private long id;
    private String comment;
    private String userName;
}
