package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommentsConverterTest {

    @Mock
    private UsersRepository usersRepository;

    private CommentsConverter commentsConverter;

    @Before
    public void setUp() {
        commentsConverter = new CommentsConverter(usersRepository);
    }

    @Test
    public void convertToEntity() {
        final CommentsDto commentsDto = MockData.commentsDto();
        final UsersEntity usersEntity = MockData.usersEntity();
        Mockito.when(usersRepository.findByLogin(commentsDto.getUserName())).thenReturn(usersEntity);
        final CommentsEntity commentsEntity = commentsConverter.convertToEntity(commentsDto);
        assertEquals(commentsDto.getComment(), commentsEntity.getComment());
        assertEquals(commentsDto.getUserName(), commentsEntity.getUser().getLogin());
    }

    @Test
    public void convertToDto() {
        final CommentsEntity commentsEntity = MockData.commentsEntity();
        final CommentsDto commentsDto = commentsConverter.convertToDto(commentsEntity);
        assertEquals(commentsEntity.getComment(), commentsDto.getComment());
        assertEquals(commentsEntity.getUser().getLogin(), commentsDto.getUserName());
    }
}
