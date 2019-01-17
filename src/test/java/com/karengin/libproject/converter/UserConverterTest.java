package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.repository.UsersRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

    private UserConverter userConverter;

    @Mock
    UsersRoleRepository usersRoleRepository;

    @Before
    public void setUp() {
        userConverter = new UserConverter(usersRoleRepository);
    }

    @Test
    public void convertToEntity() {
        final UsersDto usersDto = MockData.usersDto();
        final UsersEntity usersEntity = userConverter.convertToEntity(usersDto);

        assertEquals(usersDto.getLogin(), usersEntity.getLogin());
        assertEquals(usersDto.getPassword(), usersEntity.getPassword());
    }

    @Test
    public void convertToDto() {
        final UsersEntity usersEntity = MockData.usersEntity();
        final UsersDto usersDto = userConverter.convertToDto(usersEntity);

        assertEquals(usersEntity.getLogin(), usersDto.getLogin());
        assertEquals(usersEntity.getPassword(), usersDto.getPassword());
    }
}
