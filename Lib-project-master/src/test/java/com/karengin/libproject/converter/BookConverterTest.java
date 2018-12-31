package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BookConverterTest {

    @Mock
    AuthorRepository authorRepository;

    private BookConverter bookConverter;

    @Before
    public void setUp() {
        bookConverter = new BookConverter(authorRepository);
    }

    @Test
    public void convertToEntity() {
        final AuthorEntity authorEntity = MockData.authorEntity();
        final BookDto bookDto = MockData.bookDto();
        Mockito.when(authorRepository.getByName(bookDto.getAuthor())).thenReturn(authorEntity);
        final BookEntity bookEntity = bookConverter.convertToEntity(bookDto);
        assertEquals(bookDto.getTitle(), bookEntity.getTitle());
        assertEquals(bookDto.getAuthor(), bookEntity.getAuthor().getName());
        assertEquals(bookDto.getDescription(), bookEntity.getDescription());
    }

    @Test
    public void convertToDto() {
        final BookEntity bookEntity = MockData.bookEntity();
        final BookDto bookDto = bookConverter.convertToDto(bookEntity);
        assertEquals(bookEntity.getTitle(), bookDto.getTitle());
        assertEquals(bookEntity.getAuthor().getName(), bookDto.getAuthor());
        assertEquals(bookEntity.getDescription(), bookDto.getDescription());
    }
}
