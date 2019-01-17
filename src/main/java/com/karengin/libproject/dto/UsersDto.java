package com.karengin.libproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersDto {
    private long id;
    private String password;
    private String login;
    private String role;
}
