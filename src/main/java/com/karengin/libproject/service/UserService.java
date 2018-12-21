package com.karengin.libproject.service;

import com.karengin.libproject.converter.UserConverter;
import com.karengin.libproject.dao.UsersRepository;
import com.karengin.libproject.dao.UsersRoleRepository;
import com.karengin.libproject.dbo.UsersDbo;
import com.karengin.libproject.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final UserConverter userConverter;
    private final UsersRoleRepository usersRoleRepository;

    @Autowired
    public UserService(final UsersRepository usersRepository, final UserConverter userConverter,
                       final UsersRoleRepository usersRoleRepository) {
        this.usersRepository = usersRepository;
        this.userConverter = userConverter;
        this.usersRoleRepository = usersRoleRepository;
    }

    public void register(final UsersDto usersDto) {
        UsersDbo user = userConverter.convertToDbo(usersDto);
        user.setUserRole(usersRoleRepository.findById(2));
        usersRepository.save(user);
    }

    public UsersDto findByLogin(String login) {
        return userConverter.convertToDto(usersRepository.findByLogin(login));
    }
}
