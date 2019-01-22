package com.karengin.libproject.service;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.Entity.UsersRoleEntity;
import com.karengin.libproject.converter.AbstractConverter;
import com.karengin.libproject.converter.UserConverter;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import com.karengin.libproject.dto.UsersDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService<UsersDto, UsersEntity, UsersRepository>{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRoleRepository roleRepository;

    public UserService(UsersRepository repository, AbstractConverter<UsersDto, UsersEntity> converter) {
        super(repository, converter);
    }

    @Override
    public ResponseEntity<String> save(final UsersDto usersDto) {
        if (getRepository().findByLogin(usersDto.getLogin()) != null) {
            return ResponseEntity.status(403).body("Login already exists");
        }

        UsersEntity user = getConverter().convertToEntity(usersDto);
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        getRepository().save(user);
        return ResponseEntity.status(201).body("Account was created");
    }

    public ResponseEntity<List<UsersDto>> findByLoginContainString(final String filter) {
        return ResponseEntity.status(200).body(
                getRepository().findAllByLoginContains(filter).stream()
                        .map(getConverter()::convertToDto).collect(Collectors.toList()));
    }

    public ResponseEntity<Long> usersCount(final String filter) {
        if (filter == null) {
            return ResponseEntity.status(200).body(getRepository().count());
        }
        return ResponseEntity.status(200).body(getRepository().countByLoginContains(filter));
    }

    public ResponseEntity<List<UsersRoleEntity>> getAllRoles() {
        return ResponseEntity.status(200).body(roleRepository.findAll());
    }

    public ResponseEntity<String> editUser(final UsersDto usersDto) {
        getRepository().save(getConverter().convertToEntity(usersDto));
        return ResponseEntity.status(201).body("User was edited");
    }

    public ResponseEntity<Boolean> findByLogin(final String login, final UsersDto usersDto) {
        final UsersEntity usersEntity = getRepository().findByLogin(login);
        if (usersDto != null) {
            if (usersEntity != null && usersEntity.getId() != usersDto.getId()) {
                return ResponseEntity.status(200).body(true);
            }
        } else if (usersEntity != null) {
            return ResponseEntity.status(200).body(true);
        }
        return ResponseEntity.status(200).body(false);
    }

    @Override
    protected boolean beforeSave(UsersDto dto) {
        return false;
    }

    @Override
    public List<UsersDto> findByFilterParameter(String filterParameter) {
        return null;
    }
}
