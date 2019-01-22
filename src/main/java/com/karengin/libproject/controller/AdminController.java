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
        return authorService.save(authorDto);
    }

    @PostMapping("/create_book")
    public ResponseEntity<String> createBook(@RequestBody final BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @DeleteMapping("/delete_comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") final long id) {
        return commentsService.deleteById(id);
    }

    /*author Stanislav Patskevich */
    @DeleteMapping("/delete_author/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") final long id) {
        return authorService.deleteAuthor(id);
    }

    @PostMapping("/change_author/{id}/{new_name}")
    public ResponseEntity<String> changeAuthor(@PathVariable("id") final long id, @PathVariable("id") final String new_name) {
        return authorService.сhangeAuthor(id, new_name);
    }

    @PostMapping("/change_book/{id}")
    public ResponseEntity<String> changeBook(@PathVariable("id") final long id, @RequestBody final BookDto bookDto) {
        return bookService.changeBookNew(id, bookDto);
    }
}
