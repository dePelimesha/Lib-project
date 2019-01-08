package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.converter.GenreConverter;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.GenreRepository;
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
public class GenreServiceTest {
    private GenreService genreService;

    @Mock
    GenreRepository genreRepository;

    @Mock
    GenreConverter genreConverter;

    @Before
    public void setUp() {
        genreService = new GenreService(genreRepository, genreConverter);
    }

    @Test
    public void getGenreList() {
        final GenreDto genreDto = MockData.genreDto();
        final GenreEntity genreEntity = MockData.genreEntity();
        final List<GenreEntity> genreEntities = Arrays.asList(genreEntity, genreEntity);

        Mockito.when(genreRepository.findAll()).thenReturn(genreEntities);
        Mockito.when(genreConverter.convertToDto(genreEntity)).thenReturn(genreDto);

        ResponseEntity<List<GenreDto>> result = genreService.getGenresList();
        List<GenreDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(resultList.size(), genreEntities.size());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(genreRepository, times(1)).findAll();
        verify(genreConverter, times(2)).convertToDto(genreEntity);
        resultList.forEach(genre -> {
            assertEquals(genre.getGenre(),genreEntity.getGenre());
        });
    }
}
