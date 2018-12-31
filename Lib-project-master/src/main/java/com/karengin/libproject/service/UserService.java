package com.karengin.libproject.service;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.converter.UserConverter;
import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import com.karengin.libproject.dto.UsersDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final UserConverter userConverter;
    private final UsersRoleRepository usersRoleRepository;
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(final UsersDto usersDto) {
        if (usersRepository.findByLogin(usersDto.getLogin()) != null) {
            return ResponseEntity.status(403).body("Login already exists");
        }

        UsersEntity user = userConverter.convertToEntity(usersDto);
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setUser_role(usersRoleRepository.findById(2));
        usersRepository.save(user);
        return ResponseEntity.status(201).body("Account was created");
    }
}
