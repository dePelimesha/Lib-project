package com.karengin.libproject.service;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.BookRepository;
import com.karengin.libproject.repository.CommentsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class CommentsServiceTest {

    CommentsService commentsService;

    @Mock
    CommentsRepository commentsRepository;

    @Mock
    CommentsConverter commentsConverter;

    @Mock
    BookRepository bookRepository;

    @Before
    public void setUp() {
        commentsService = new CommentsService(commentsRepository, commentsConverter, bookRepository);
    }

    @Test
    public void deleteById() {
        CommentsEntity commentsEntity = MockData.commentsEntity();

        Mockito.when(commentsRepository.findById(commentsEntity.getId())).thenReturn(commentsEntity);
        Mockito.doNothing().when(commentsRepository).deleteById(commentsEntity.getId());

        ResponseEntity<String> result = commentsService.deleteById(commentsEntity.getId());

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(),"Comment was deleted");
        verify(commentsRepository, times(1)).findById(commentsEntity.getId());
        verify(commentsRepository, times(1)).deleteById(commentsEntity.getId());
    }

    @Test
    public void deleteByIdNoExists() {
        CommentsEntity commentsEntity = MockData.commentsEntity();

        Mockito.when(commentsRepository.findById(commentsEntity.getId())).thenReturn(null);

        ResponseEntity<String> result = commentsService.deleteById(commentsEntity.getId());
        verify(commentsRepository, times(1)).findById(commentsEntity.getId());

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(result.getBody(),"Comment doesn't exist");
    }

    @Test
    public void createComment() {
        CommentsDto commentsDto = MockData.commentsDto();
        CommentsEntity commentsEntity = MockData.commentsEntity();
        BookEntity bookEntity = MockData.bookEntity();

        Mockito.when(commentsConverter.convertToEntity(commentsDto)).thenReturn(commentsEntity);
        Mockito.when(bookRepository.findById(bookEntity.getId())).thenReturn(bookEntity);
        Mockito.when(commentsRepository.save(commentsEntity)).thenReturn(commentsEntity);

        ResponseEntity<String> result = commentsService.createComment(bookEntity.getId(), commentsDto);

        assertNotNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody(),"Comment was added");
        verify(commentsConverter, times(1)).convertToEntity(commentsDto);
        verify(bookRepository, times(1)).findById(bookEntity.getId());
        verify(commentsRepository, times(1)).save(commentsEntity);
    }
}
