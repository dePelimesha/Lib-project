package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.CommentsDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AuthorConverterTest {

    private AuthorConverter authorConverter;

    @Before
    public void setUp() {
        authorConverter = new AuthorConverter();
    }

    @Test
    public void convertToEntity() {
        final AuthorDto authorDto = MockData.authorDto();
        final AuthorEntity authorEntity = authorConverter.convertToEntity(authorDto);

        assertEquals(authorDto.getName(), authorEntity.getName());
    }

    @Test
    public void convertToDto() {
        final AuthorEntity authorEntity = MockData.authorEntity();
        final AuthorDto authorDto = authorConverter.convertToDto(authorEntity);

        assertEquals(authorEntity.getName(), authorDto.getName());
    }
}
