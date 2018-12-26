package com.karengin.libproject.controller;

import com.karengin.libproject.MockData;
import com.karengin.libproject.config.AuthenticationEntryPoint;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.CommentsService;
import com.karengin.libproject.service.UserDetailsServiceImpl;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private CommentsService commentsService;

    @Test
    public void getBooksList() throws Exception {
        final BookDto bookDto = MockData.bookDto();
        final List<BookDto> bookDtoList = Arrays.asList(bookDto,bookDto);

        Mockito.when(bookService.getBooksList())
                .thenReturn(ResponseEntity.ok(bookDtoList));

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

    @Test
    public void getBookById() throws Exception {
        final BookDto bookDto = MockData.bookDto();

        Mockito.when(bookService.getBookById(bookDto.getId()))
                .thenReturn(ResponseEntity.ok(bookDto));

        mockMvc.perform(get("/books/list/{id}", bookDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is(equalTo(bookDto.getTitle()))))
                .andExpect(jsonPath("$.author", is(equalTo(bookDto.getAuthor()))))
                .andExpect(jsonPath("$.description", is(equalTo(bookDto.getDescription()))));
    }

    @Test
    public void getCommentsForBook() throws Exception {
        final CommentsDto commentsDto = MockData.commentsDto();
        final BookDto bookDto = MockData.bookDto();
        final List<CommentsDto> commentsDtoList = Arrays.asList(commentsDto, commentsDto);

        Mockito.when(bookService.getCommentsForBook(bookDto.getId()))
                .thenReturn(ResponseEntity.ok(commentsDtoList));

        mockMvc.perform(get("/books/list/{id}/comments", bookDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", isA(JSONArray.class)))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].comment", is(equalTo(commentsDto.getComment()))))
                .andExpect(jsonPath("$[0].userName", is(equalTo(commentsDto.getUserName()))))
                .andExpect(jsonPath("$[1].comment", is(equalTo(commentsDto.getComment()))))
                .andExpect(jsonPath("$[1].userName", is(equalTo(commentsDto.getUserName()))));
    }

    @Test
    public void getAuthorsList() throws Exception {
        final AuthorDto authorDto = MockData.authorDto();
        final List<AuthorDto> authorDtoList = Arrays.asList(authorDto, authorDto);

        Mockito.when(authorService.getAuthorsList())
                .thenReturn(ResponseEntity.ok(authorDtoList));

        mockMvc.perform(get("/books/auth_list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", isA(JSONArray.class)))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].name", is(equalTo(authorDto.getName()))))
                .andExpect(jsonPath("$[1].name", is(equalTo(authorDto.getName()))));
    }

    @Test
    public void getBookByAuthorId() throws Exception {
        final AuthorDto authorDto = MockData.authorDto();
        final BookDto bookDto = MockData.bookDto();
        final List<BookDto> bookDtoList = Arrays.asList(bookDto,bookDto);

        Mockito.when(bookService.getBooksListByAuthorId(authorDto.getId()))
                .thenReturn(ResponseEntity.ok(bookDtoList));

        mockMvc.perform(get("/books/auth_list/{id}", authorDto.getId()))
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

    @Test
    @WithMockUser(username = "testUser", password = "pass123", roles = "ADMIN")
    public void addComment() throws Exception {
        final CommentsDto commentsDto = MockData.commentsDto();
        final BookDto bookDto = MockData.bookDto();

        Mockito.when(commentsService.createComment(bookDto.getId(), commentsDto)).
                thenReturn(ResponseEntity.status(201).body("Comment was added"));

        mockMvc.perform(post("/books/list/{id}/add_comment", bookDto.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(commentsDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Comment was added"));
    }
}

