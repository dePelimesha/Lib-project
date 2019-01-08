package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.repository.GenreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class GenreConverterTest {

    @Mock
    GenreRepository genreRepository;

    private GenreConverter genreConverter;

    @Before
    public void setUp() {
        genreConverter = new GenreConverter(genreRepository);
    }

    @Test
    public void convertToEntity() {
        final GenreDto genreDto = MockData.genreDto();
        final GenreEntity genreEntity = genreConverter.convertToEntity(genreDto);
        assertEquals(genreDto.getGenre(), genreEntity.getGenre());
    }

    @Test
    public void convertToDto() {
        final GenreEntity genreEntity = MockData.genreEntity();
        final GenreDto genreDto= genreConverter.convertToDto(genreEntity);
        assertEquals(genreEntity.getGenre(), genreDto.getGenre());
    }

    @Test
    public void convertListToEntity() {
        final GenreEntity genreEntity = MockData.genreEntity();
        final List<String> stringList = Arrays.asList(genreEntity.getGenre(), genreEntity.getGenre());

        Mockito.when(genreRepository.getByGenre(genreEntity.getGenre()))
                .thenReturn(genreEntity);

        final List<GenreEntity> genreEntities = genreConverter.convertToEntity(stringList);

        genreEntities.forEach(genre-> assertEquals(genreEntity.getGenre(), genre.getGenre()));
        verify(genreRepository, times(2)).getByGenre(genreEntity.getGenre());
    }

    @Test
    public void convertListToString() {
        final GenreEntity genreEntity = MockData.genreEntity();
        final List<GenreEntity> entityList = Arrays.asList(genreEntity, genreEntity);

        final List<String> stringList = genreConverter.convertToDto(entityList);

        stringList.forEach(genre -> assertEquals(genreEntity.getGenre(), genre));
    }
}
