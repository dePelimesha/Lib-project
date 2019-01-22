package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class AuthorServiceTest {

    private AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    BookRepository bookRepository;

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

        ResponseEntity<String> result = authorService.save(authorDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(),"Author was added");
        verify(authorRepository, times(1)).getByName(authorDto.getName());
        verify(authorConverter, times(1)).convertToEntity(authorDto);
        verify(authorRepository, times(1)).save(authorEntity);
    }

    /*author Stanislav Patskevich */
    @Test
    public void deleteAuthor(){

        final AuthorEntity author = new AuthorEntity();
        final AuthorEntity noAuthor = new AuthorEntity();
        final BookEntity book1 = new BookEntity();
        final BookEntity book2 = new BookEntity();
        final List<BookEntity> listBook = new ArrayList<>();

        author.setId(2);
        author.setName("author");
        noAuthor.setId(1);
        noAuthor.setName("No author");
        book1.setId(1);
        book1.setTitle("title 1");
        book1.setDescription("description 1");
        book2.setId(2);
        book2.setTitle("title 2");
        book2.setDescription("description 2");
        listBook.add(book1);
        listBook.add(book2);
        author.setBooks(listBook);
        Mockito.when(authorRepository.existsById(new Long(2))).thenReturn(true);
        Mockito.when(authorRepository.findById(2)).thenReturn(author);
        Mockito.when(authorRepository.findById(1)).thenReturn(noAuthor);


        ResponseEntity<String> result = authorService.deleteAuthor(2);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(),"Автор с ID № 2 был удалён");
        assertEquals(book1.getAuthor(), noAuthor);
        assertEquals(book2.getAuthor(), noAuthor);
        verify(bookRepository, times(2)).save(any(BookEntity.class));
        verify(authorRepository, times(1)).delete(author);

    }

    @Test
    public void сhangeAuthor(){

        final AuthorEntity author = new AuthorEntity();
        author.setId(1);
        author.setName("author");

        Mockito.when(authorRepository.existsById(new Long(1))).thenReturn(true);
        Mockito.when(authorRepository.findById(1)).thenReturn(author);


        ResponseEntity<String> result = authorService.сhangeAuthor(1, "new name");

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(),"Автор с ID № 1 был изменён");
        assertEquals(author.getName(), "new name");
        verify(authorRepository, times(1)).save(author);

    }
}
