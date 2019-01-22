package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.converter.BookConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.converter.GenreConverter;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.BookRepository;
import com.karengin.libproject.repository.CommentsRepository;
import com.karengin.libproject.repository.GenreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    private BookService bookService;

    @Mock
    BookConverter bookConverter;

    @Mock
    BookRepository bookRepository;

    @Mock
    CommentsConverter commentsConverter;

    @Mock
    CommentsRepository commentsRepository;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    GenreConverter genreConverter;

    @Before
    public void setUp() {
        bookService = new BookService(bookRepository, bookConverter);
    }

    @Test
    public void createBook() {
        final BookDto bookDto = MockData.bookDto();
        final BookEntity bookEntity = MockData.bookEntity();
        final AuthorEntity authorEntity = MockData.authorEntity();
        final GenreEntity genreEntity = MockData.genreEntity();

        Mockito.when(authorRepository.getByName(authorEntity.getName())).thenReturn(authorEntity);
        Mockito.when(genreRepository.getByGenre(genreEntity.getGenre())).thenReturn(genreEntity);
        Mockito.when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
        Mockito.when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        ResponseEntity<String> result = bookService.save(bookDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(),"Book was added");
        verify(authorRepository, times(1)).getByName(authorEntity.getName());
        verify(genreRepository, times(2)).getByGenre(genreEntity.getGenre());
        verify(bookConverter, times(1)).convertToEntity(bookDto);
        verify(bookRepository, times(1)).save(bookEntity);
    }

    @Test
    public void createBookWithNewAuthor() {
        final BookDto bookDto = MockData.bookDto();
        final BookEntity bookEntity = MockData.bookEntity();
        final GenreEntity genreEntity = MockData.genreEntity();
        final AuthorEntity newAuthor = new AuthorEntity();
        newAuthor.setId(0);
        newAuthor.setName("WRONG_NAME");
        bookDto.setAuthor(newAuthor.getName());
        bookEntity.setAuthor(newAuthor);

        Mockito.when(authorRepository.getByName(newAuthor.getName())).thenReturn(null);
        Mockito.when(genreRepository.getByGenre(genreEntity.getGenre())).thenReturn(genreEntity);
        Mockito.when(authorRepository.save(newAuthor)).thenReturn(newAuthor);
        Mockito.when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
        Mockito.when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        ResponseEntity<String> result = bookService.save(bookDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(),"Book was added");
        verify(authorRepository, times(1)).getByName(newAuthor.getName());
        verify(authorRepository, times(1)).save(newAuthor);
        verify(genreRepository, times(2)).getByGenre(genreEntity.getGenre());
        verify(bookConverter, times(1)).convertToEntity(bookDto);
        verify(bookRepository, times(1)).save(bookEntity);
    }

    @Test
    public void getBooksList() {
        final BookEntity bookEntity = MockData.bookEntity();
        final BookDto bookDto = MockData.bookDto();
        final String genreString = "genre";
        final List<BookEntity> bookEntityList = Arrays.asList(bookEntity, bookEntity);

        Mockito.when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);
        Mockito.when(bookRepository.findAll()).thenReturn(bookEntityList);

        ResponseEntity<List<BookDto>> result = bookService.getBooksList(null);
        final List<BookDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(resultList.size(), bookEntityList.size());
        verify(bookConverter, times(2)).convertToDto(bookEntity);
        verify(bookRepository, times(1)).findAll();
        resultList.forEach(book -> {
            assertEquals(book.getTitle(), bookEntity.getTitle());
            assertEquals(book.getAuthor(), bookEntity.getAuthor().getName());
            assertEquals(book.getDescription(), bookEntity.getDescription());
            book.getGenres().forEach(genre -> assertEquals(genreString, genre));
        });
    }

    @Test
    public void getBooksListWithGenre() {
        final BookEntity bookEntity = MockData.bookEntity();
        final BookDto bookDto = MockData.bookDto();
        final String[] genreArray = {"genre", "genre"};
        final GenreEntity genreEntity = MockData.genreEntity();
        genreEntity.setBooks(Arrays.asList(bookEntity, bookEntity));
        final List<BookEntity> bookEntityList = Arrays.asList(bookEntity, bookEntity);

        Mockito.when(genreRepository.getByGenre(genreEntity.getGenre())).thenReturn(genreEntity);
        Mockito.when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);

        ResponseEntity<List<BookDto>> result = bookService.getBooksList(genreArray);
        final List<BookDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(resultList.size(), bookEntityList.size());
        verify(bookConverter, times(2)).convertToDto(bookEntity);
        verify(genreRepository, times(2)).getByGenre(genreEntity.getGenre());
        resultList.forEach(book -> {
            assertEquals(book.getTitle(), bookEntity.getTitle());
            assertEquals(book.getAuthor(), bookEntity.getAuthor().getName());
            assertEquals(book.getDescription(), bookEntity.getDescription());
            book.getGenres().forEach(genre -> assertEquals(genreArray[0], genre));
        });
    }

    @Test
    public void getBooksByAuth() {
        final BookEntity bookEntity = MockData.bookEntity();
        final BookDto bookDto = MockData.bookDto();
        final List<BookEntity> bookEntityList = Arrays.asList(bookEntity, bookEntity);
        final AuthorEntity authorEntity = MockData.authorEntity();
        final GenreEntity genreEntity = MockData.genreEntity();

        Mockito.when(authorRepository.findById(authorEntity.getId())).thenReturn(authorEntity);
        Mockito.when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);
        Mockito.when(bookRepository.findAllByAuthor_Id(authorEntity.getId())).thenReturn(bookEntityList);

        ResponseEntity<List<BookDto>> result = bookService.getBooksListByAuthorId(authorEntity.getId());
        final List<BookDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(resultList.size(), bookEntityList.size());
        verify(authorRepository, times(1)).findById(authorEntity.getId());
        verify(bookConverter, times(2)).convertToDto(bookEntity);
        verify(bookRepository, times(1)).findAllByAuthor_Id(authorEntity.getId());
        resultList.forEach(book -> {
            assertEquals(book.getTitle(), bookEntity.getTitle());
            assertEquals(book.getAuthor(), bookEntity.getAuthor().getName());
            assertEquals(book.getDescription(), bookEntity.getDescription());
            book.getGenres().forEach(genre -> assertEquals(genreEntity.getGenre(), genre));
        });
    }

    @Test
    public void getBookById() {
        final BookEntity bookEntity = MockData.bookEntity();
        final BookDto bookDto = MockData.bookDto();
        final GenreEntity genreEntity = MockData.genreEntity();

        Mockito.when(bookRepository.findById(bookEntity.getId())).thenReturn(bookEntity);
        Mockito.when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);

        ResponseEntity<BookDto> result = bookService.getBookById(bookEntity.getId());

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(bookRepository, times(2)).findById(bookEntity.getId());
        verify(bookConverter, times(1)).convertToDto(bookEntity);
        assertEquals(result.getBody().getTitle(), bookEntity.getTitle());
        assertEquals(result.getBody().getAuthor(), bookEntity.getAuthor().getName());
        assertEquals(result.getBody().getDescription(), bookEntity.getDescription());
        result.getBody().getGenres().forEach(genre -> assertEquals(genreEntity.getGenre(), genre));
    }

    @Test
    public void getComments() {
        final BookEntity bookEntity = MockData.bookEntity();
        final CommentsEntity commentsEntity = MockData.commentsEntity();
        final CommentsDto commentsDto = MockData.commentsDto();
        final List<CommentsEntity> commentsEntityList = Arrays.asList(commentsEntity, commentsEntity);

        Mockito.when(bookRepository.findById(bookEntity.getId())).thenReturn(bookEntity);
        Mockito.when(commentsConverter.convertToDto(commentsEntity)).thenReturn(commentsDto);
        Mockito.when(commentsRepository.findAllByBookId(bookEntity.getId())).thenReturn(commentsEntityList);

        ResponseEntity<List<CommentsDto>> result = bookService.getCommentsForBook(bookEntity.getId());
        List<CommentsDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(resultList.size(), commentsEntityList.size());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(bookRepository, times(1)).findById(bookEntity.getId());
        verify(commentsConverter, times(2)).convertToDto(commentsEntity);
        verify(commentsRepository, times(1)).findAllByBookId(bookEntity.getId());
        resultList.forEach(comment -> {
            assertEquals(comment.getComment(), commentsEntity.getComment());
            assertEquals(comment.getUserName(), commentsEntity.getUser().getLogin());
        });
    }

    @Test
    public void searchByTitle() {
        final BookEntity bookEntity = MockData.bookEntity();
        final List<BookEntity> bookEntityList = Arrays.asList(bookEntity, bookEntity);
        final BookDto bookDto = MockData.bookDto();
        final GenreEntity genreEntity = MockData.genreEntity();

        Mockito.when(bookRepository.findAllByTitleStartsWith(bookEntity.getTitle())).thenReturn(bookEntityList);
        Mockito.when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);

        ResponseEntity<List<BookDto>> result = bookService.getBooksByTitle(bookEntity.getTitle());
        final List<BookDto> resultList = result.getBody();

        assertNotNull(result.getBody());
        assertEquals(resultList.size(), bookEntityList.size());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(bookRepository, times(1)).findAllByTitleStartsWith(bookEntity.getTitle());
        verify(bookConverter, times(2)).convertToDto(bookEntity);
        resultList.forEach(book -> {
            assertEquals(book.getTitle(), bookEntity.getTitle());
            assertEquals(book.getAuthor(), bookEntity.getAuthor().getName());
            assertEquals(book.getDescription(), bookEntity.getDescription());
            book.getGenres().forEach(genre -> assertEquals(genreEntity.getGenre(), genre));
        });
    }

    @Test
    public void searchByTitleReturnNull() {
        final String search = "search";
        final List<BookEntity> bookEntityList =  new ArrayList<>();

        Mockito.when(bookRepository.findAllByTitleStartsWith(search)).thenReturn(bookEntityList);

        ResponseEntity<List<BookDto>> result = bookService.getBooksByTitle(search);

        assertNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(bookRepository, times(1)).findAllByTitleStartsWith(search);
    }
  
    /*author Stanislav Patskevich */
//    @Test
//    public void deleteBook(){
//
//        final BookEntity book = new BookEntity();
//        final CommentsEntity comment1 = new CommentsEntity();
//        final CommentsEntity comment2 = new CommentsEntity();
//        final List<CommentsEntity> listComment = new ArrayList<>();
//
//        book.setId(1);
//        listComment.add(comment1);
//        listComment.add(comment2);
//        book.setComments(listComment);
//        Mockito.when(bookRepository.existsById(new Long(1))).thenReturn(true);
//        Mockito.when(bookRepository.findById(1)).thenReturn(book);
//
//
//        ResponseEntity<String> result = bookService.deleteBook(1);
//
//        assertNotNull(result.getBody());
//        assertEquals(result.getStatusCode(), HttpStatus.OK);
//        assertEquals(result.getBody(),"Была удалена книга с ID №1");
//        verify(commentsRepository, times(2)).delete(any(CommentsEntity.class));
//        verify(bookRepository, times(1)).delete(book);
//
//    }

    @Test
    public void changeBook(){

        final BookEntity book = new BookEntity();
        final AuthorEntity author = new AuthorEntity();
        final BookDto bookDto = new BookDto();

        author.setId(1);
        author.setName("name");
        book.setId(1);
        book.setTitle("title");
        book.setDescription("description");
        bookDto.setId(1L);
        bookDto.setTitle("new title");
        bookDto.setDescription("new description");
        bookDto.setAuthor("name");

        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);
        Mockito.when(bookRepository.findById(1)).thenReturn(book);
        Mockito.when(authorRepository.existsByName(bookDto.getAuthor())).thenReturn(true);
        Mockito.when(authorRepository.findByName(bookDto.getAuthor())).thenReturn(author);

        final ResponseEntity<String> result = bookService.changeBookNew(1, bookDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(),"Книга с ID №1 была изменена!");
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getDescription(), bookDto.getDescription());
        assertEquals(book.getAuthor(), author);
        verify(bookRepository, times(1)).save(book);


        Mockito.when(authorRepository.existsByName(bookDto.getAuthor())).thenReturn(false);

        final ResponseEntity<String> newResult = bookService.changeBookNew(1, bookDto);
        verify( authorRepository, times(1)).save(any(AuthorEntity.class));
    }
//
//    @Test
//    public void getBookByName(){
//
//        final BookEntity book = MockData.bookEntity();
//
//        Mockito.when(bookRepository.findByTitle(book.getTitle())).thenReturn(book);
//
//        final ResponseEntity<BookDto> result = bookService.getBookByName(book.getTitle());;
//
//        verify(  bookConverter, times(1)).convertToDto(book);
//        assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
}