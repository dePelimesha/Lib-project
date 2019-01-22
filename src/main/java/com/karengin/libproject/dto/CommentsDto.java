package com.karengin.libproject.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CommentsDto extends AbstractDto {
    private String comment;
    private String userName;
}
