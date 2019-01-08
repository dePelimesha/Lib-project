package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BookConverterTest {

    @Mock
    AuthorRepository authorRepository;

    @Mock
    GenreConverter genreConverter;

    private BookConverter bookConverter;

    @Before
    public void setUp() {
        bookConverter = new BookConverter(authorRepository, genreConverter);
    }

    @Test
    public void convertToEntity() {
        final AuthorEntity authorEntity = MockData.authorEntity();
        final GenreEntity genreEntity = MockData.genreEntity();
        final List<GenreEntity> genreList = Arrays.asList(genreEntity, genreEntity);
        final BookDto bookDto = MockData.bookDto();

        Mockito.when(authorRepository.getByName(bookDto.getAuthor()))
                .thenReturn(authorEntity);
        Mockito.when(genreConverter.convertToEntity(bookDto.getGenres()))
                .thenReturn(genreList);

        final BookEntity bookEntity = bookConverter.convertToEntity(bookDto);

        assertEquals(bookDto.getTitle(), bookEntity.getTitle());
        assertEquals(bookDto.getAuthor(), bookEntity.getAuthor().getName());
        assertEquals(bookDto.getDescription(), bookEntity.getDescription());
        bookEntity.getGenresList().forEach(genre ->
                assertEquals(genreEntity.getGenre(), genre.getGenre()));
    }

    @Test
    public void convertToDto() {
        final String genreString = "genre";
        final List<String> stringList = Arrays.asList(genreString, genreString);
        final BookEntity bookEntity = MockData.bookEntity();

        Mockito.when(genreConverter.convertToDto(bookEntity.getGenresList()))
                .thenReturn(stringList);

        final BookDto bookDto = bookConverter.convertToDto(bookEntity);

        assertEquals(bookEntity.getTitle(), bookDto.getTitle());
        assertEquals(bookEntity.getAuthor().getName(), bookDto.getAuthor());
        assertEquals(bookEntity.getDescription(), bookDto.getDescription());
        bookDto.getGenres().forEach(genre -> assertEquals(genreString, genre));
    }
}
