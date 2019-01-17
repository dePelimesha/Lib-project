package com.karengin.libproject.service;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.Entity.UsersRoleEntity;
import com.karengin.libproject.converter.UserConverter;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import com.karengin.libproject.dto.UsersDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        usersRepository.save(user);
        return ResponseEntity.status(201).body("Account was created");
    }

    public ResponseEntity<List<UsersDto>> getAll() {
        return ResponseEntity.status(200).body(
                usersRepository.findAll().stream()
                        .map(userConverter::convertToDto).collect(Collectors.toList()));
    }

    public ResponseEntity<List<UsersDto>> findByLoginContainString(final String filter) {
        return ResponseEntity.status(200).body(
                usersRepository.findAllByLoginContains(filter).stream()
                        .map(userConverter::convertToDto).collect(Collectors.toList()));
    }

    public ResponseEntity<Long> usersCount(final String filter) {
        if (filter == null) {
            return ResponseEntity.status(200).body(usersRepository.count());
        }
        return ResponseEntity.status(200).body(usersRepository.countByLoginContains(filter));
    }

    public ResponseEntity<List<UsersRoleEntity>> getAllRoles() {
        return ResponseEntity.status(200).body(usersRoleRepository.findAll());
    }

    public ResponseEntity<String> editUser(final UsersDto usersDto) {
        usersRepository.save(userConverter.convertToEntity(usersDto));
        return ResponseEntity.status(201).body("User was edited");
    }

    public ResponseEntity<Boolean> findByLogin(final String login, final UsersDto usersDto) {
        final UsersEntity usersEntity = usersRepository.findByLogin(login);
        if (usersDto != null) {
            if (usersEntity != null && usersEntity.getId() != usersDto.getId()) {
                return ResponseEntity.status(200).body(true);
            }
        } else if (usersEntity != null) {
            return ResponseEntity.status(200).body(true);
        }
        return ResponseEntity.status(200).body(false);
    }
}
