package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class AuthorServiceTest {

    private AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorConverter authorConverter;

    @Before
    public void setUp() {
        authorService = new AuthorService(authorRepository, authorConverter);
    }

    @Test
    public void getAuthorsList() {
        final AuthorDto authorDto = MockData.authorDto();
        final AuthorEntity authorEntity = MockData.authorEntity();
        final List<AuthorEntity> authorEntityList = Arrays.asList(authorEntity, authorEntity);

        Mockito.when(authorRepository.findAll()).thenReturn(authorEntityList);
        Mockito.when(authorConverter.convertToDto(authorEntity)).thenReturn(authorDto);

        ResponseEntity<List<AuthorDto>> result = authorService.getAuthorsList();
        List<AuthorDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(resultList.size(), authorEntityList.size());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(authorRepository, times(1)).findAll();
        verify(authorConverter, times(2)).convertToDto(authorEntity);
        resultList.forEach(author -> {
            assertEquals(author.getName(), authorEntity.getName());
        });
    }

    @Test
    public void addAuthor() {
        final AuthorEntity authorEntity = MockData.authorEntity();
        final AuthorDto authorDto = MockData.authorDto();

        Mockito.when(authorRepository.getByName(authorDto.getName())).thenReturn(null);
        Mockito.when(authorConverter.convertToEntity(authorDto)).thenReturn(authorEntity);
        Mockito.when(authorRepository.save(authorEntity)).thenReturn(authorEntity);

        ResponseEntity<String> result = authorService.addAuthor(authorDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(),"Author was added");
        verify(authorRepository, times(1)).getByName(authorDto.getName());
        verify(authorConverter, times(1)).convertToEntity(authorDto);
        verify(authorRepository, times(1)).save(authorEntity);
    }
}
