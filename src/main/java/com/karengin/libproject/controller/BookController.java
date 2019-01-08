package com.karengin.libproject.controller;

import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.service.AuthorService;
import com.karengin.libproject.service.BookService;
import com.karengin.libproject.service.CommentsService;
import com.karengin.libproject.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CommentsService commentsService;
    private final GenreService genreService;

    @GetMapping("/list")
    public ResponseEntity<List<BookDto>> getAllBook(final HttpServletRequest request) {
        final String[] genres = request.getParameterValues("genre");
        return bookService.getBooksList(genres);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") final long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/list/search/{search_value}")
    public ResponseEntity<List<BookDto>> findByTitle(@PathVariable("search_value") final String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/list/{book_id}/comments")
    public ResponseEntity<List<CommentsDto>> getComments(@PathVariable("book_id") final long id) {
        return bookService.getCommentsForBook(id);
    }

    @PostMapping("/list/{book_id}/add_comment")
    public ResponseEntity<String> addComments(@PathVariable("book_id") final long id, @RequestBody CommentsDto commentsDto) {
        commentsDto.setUserName(getPrincipal());
        return commentsService.createComment(id, commentsDto);
    }

    @GetMapping("/auth_list")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        return authorService.getAuthorsList();
    }

    @GetMapping("/auth_list/{id}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable("id") final long id) {
        return bookService.getBooksListByAuthorId(id);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getGenres() {
        return genreService.getGenresList();
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
