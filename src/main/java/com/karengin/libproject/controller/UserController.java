package com.karengin.libproject.controller;

import com.karengin.libproject.converter.UserConverter;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UsersDto usersDto) {
        if(userService.findByLogin(usersDto.getLogin()) == null) {
            userService.register(usersDto);
        }

        return "Login already exists";
    }
}
