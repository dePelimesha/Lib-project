package com.karengin.libproject.service;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.Entity.UsersRoleEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.converter.UserConverter;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UsersRoleRepository usersRoleRepository;

    @Before
    public void setUp() {
        userService = new UserService(usersRepository,userConverter);
    }

    @Test
    public void register() {
        final UsersEntity usersEntity = MockData.usersEntity();
        final UsersDto usersDto = MockData.usersDto();
        final UsersRoleEntity usersRoleEntity = MockData.usersRoleEntity();

        Mockito.doReturn(null).when(usersRepository).findByLogin(usersDto.getLogin());
        Mockito.when(userConverter.convertToEntity(usersDto)).thenReturn(usersEntity);
        Mockito.when(usersRoleRepository.findById(2)).thenReturn(usersRoleEntity);
        Mockito.when(usersRepository.save(usersEntity)).thenReturn(usersEntity);

        final ResponseEntity<String> result = userService.save(usersDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(),"Account was created");
        verify(usersRepository, times(1)).findByLogin(usersDto.getLogin());
        verify(userConverter, times(1)).convertToEntity(usersDto);
        verify(usersRoleRepository, times(1)).findById(2);
        verify(usersRepository, times(1)).save(usersEntity);
    }

    @Test
    public void registerWithExistedLogin() {
        final UsersEntity usersEntity = MockData.usersEntity();
        final UsersDto usersDto = MockData.usersDto();

        Mockito.doReturn(usersEntity).when(usersRepository).findByLogin(usersDto.getLogin());

        final ResponseEntity<String> result = userService.save(usersDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
        assertEquals(result.getBody(),"Login already exists");
        verify(usersRepository, times(1)).findByLogin(usersDto.getLogin());
    }
}
