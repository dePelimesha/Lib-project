package com.karengin.libproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karengin.libproject.MockData;
import com.karengin.libproject.config.AuthenticationEntryPoint;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.CommentsService;
import com.karengin.libproject.service.UserDetailsServiceImpl;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

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
    @WithMockUser(username = "testUser", password = "pass123", roles = "ADMIN")
    public void addAuthor() throws Exception {
        final AuthorDto authorDto = MockData.authorDto();

        Mockito.when(authorService.addAuthor(authorDto)).
                thenReturn(ResponseEntity.status(201).body("Author was added"));

        mockMvc.perform(post("/admin/create_author").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(authorDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Author was added"));
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass123", roles = "ADMIN")
    public void addBook() throws Exception {
        final BookDto bookDto = MockData.bookDto();
        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(bookService.createBook(bookDto)).
                thenReturn(ResponseEntity.status(201).body("Book was added"));

        mockMvc.perform(post("/admin/create_book").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Book was added"));
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass123", roles = "ADMIN")
    public void deleteComment() throws Exception {
        final CommentsDto commentsDto = MockData.commentsDto();

        Mockito.when(commentsService.deleteById(commentsDto.getId()))
                .thenReturn(ResponseEntity.status(200).body("Comment was deleted"));

        mockMvc.perform(delete("/admin/delete_comment/{id}", commentsDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Comment was deleted"));
    }
}
