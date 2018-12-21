package com.karengin.libproject.controller;

import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CommentsService commentsService;

    @Autowired
    public AdminController(final BookService bookService, final AuthorService authorService,
                           final CommentsService commentsService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.commentsService = commentsService;
    }

    @PostMapping("/create_book")
    public String createBook(@RequestBody final BookDto bookDto) {
        bookService.createBook(bookDto);
        return "Success";
    }

    @PostMapping("/delete_comment/{id}")
    public String deleteComment(@PathVariable("id") long id) {
        commentsService.deleteById(id);
        return "Success";
    }

    @PostMapping("/create_author")
    public String createAuthor(@RequestBody final AuthorDto authorDto) {
        if(authorService.getAuthor(authorDto.getName()) == null) {
            authorService.addAuthor(authorDto);
            return "Success";
        }

        return "Author already exists";
    }
}
