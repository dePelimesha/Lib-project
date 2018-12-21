package com.karengin.libproject.controller;

import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(final BookService bookService, final AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public List<BookDto> getAllBook() {
        return bookService.getBooksList();
    }

    @GetMapping("/list/{id}")
    public BookDto getBook(@PathVariable("id") int id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/list/{book_id}/comments")
    public List<CommentsDto> getComments(@PathVariable("book_id") int id) {
        return bookService.getCommentsForBook(id);
    }

    @PostMapping("/list/{book_id}/comments")
    public String addComments(@PathVariable("book_id") int id, @RequestBody CommentsDto commentsDto) {
        bookService.createComment(id, commentsDto);
        return "Success";
    }

    @GetMapping("/auth_list")
    public List<AuthorDto> getAuthors() {
        return authorService.getAuthorsList();
    }

    @GetMapping("/auth_list/{id}")
    public List<BookDto> getBooksByAuthor(@PathVariable("id") int id) {
        return bookService.getBooksListByAuthorId(id);
    }
}
