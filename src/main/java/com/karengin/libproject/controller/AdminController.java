package com.karengin.libproject.controller;

import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CommentsService commentsService;

    @PostMapping("/create_author")
    public ResponseEntity<String> createAuthor(@RequestBody final AuthorDto authorDto) {
        return authorService.addAuthor(authorDto);
    }

    @PostMapping("/create_book")
    public ResponseEntity<String> createBook(@RequestBody final BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @DeleteMapping("/delete_comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") final long id) {
        return commentsService.deleteById(id);
    }
}
