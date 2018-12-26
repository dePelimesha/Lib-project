package com.karengin.libproject.controller;

import com.karengin.libproject.MockData;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.CommentsService;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @Mock
    private CommentsService commentsService;

    @Test
    public void getBooksList() throws Exception {
        final BookDto bookDto = MockData.bookDto();
        final List<BookDto> bookDtoList = Arrays.asList(bookDto,bookDto);
        Mockito.when(bookService.getBooksList()).thenReturn(ResponseEntity.ok(bookDtoList));

        mockMvc.perform(get("/books/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", isA(JSONArray.class)))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].title", is(equalTo(bookDto.getTitle()))))
                .andExpect(jsonPath("$[0].author", is(equalTo(bookDto.getAuthor()))))
                .andExpect(jsonPath("$[0].description", is(equalTo(bookDto.getDescription()))))
                .andExpect(jsonPath("$[1].title", is(equalTo(bookDto.getTitle()))))
                .andExpect(jsonPath("$[1].author", is(equalTo(bookDto.getAuthor()))))
                .andExpect(jsonPath("$[1].description", is(equalTo(bookDto.getDescription()))));

    }
}
