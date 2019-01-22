package com.karengin.libproject.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UsersDto extends AbstractDto {
    private String password;
    private String login;
    private String role;
}
