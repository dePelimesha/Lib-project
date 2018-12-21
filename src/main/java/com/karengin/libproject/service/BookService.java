package com.karengin.libproject.service;

import com.karengin.libproject.converter.BookConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.dao.AuthorRepository;
import com.karengin.libproject.dao.BookRepository;
import com.karengin.libproject.dao.CommentsRepository;
import com.karengin.libproject.dao.UsersRepository;
import com.karengin.libproject.dbo.AuthorDbo;
import com.karengin.libproject.dbo.CommentsDbo;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final CommentsRepository commentsRepository;
    private final CommentsConverter commentsConverter;
    private final UsersRepository usersRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(final BookRepository bookRepository, final BookConverter bookConverter,
                       final CommentsRepository commentsRepository, final CommentsConverter commentsConverter,
                       final UsersRepository usersRepository, final AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
        this.commentsRepository = commentsRepository;
        this.commentsConverter = commentsConverter;
        this.usersRepository = usersRepository;
        this.authorRepository = authorRepository;
    }

    public void createBook(final BookDto bookDto) {
        if(bookDto.getAuthor() == null) {
            bookDto.setAuthor("No author");
        } else if (authorRepository.getByName(bookDto.getAuthor()) == null) {
            AuthorDbo authorDbo = new AuthorDbo();
            authorDbo.setName(bookDto.getAuthor());
            authorRepository.save(authorDbo);
        }
        bookRepository.save(bookConverter.convertToDbo(bookDto));
    }

    public List<BookDto> getBooksList() {
        return bookRepository.findAll().stream().map(bookConverter::convertToDto).collect(Collectors.toList());
    }

    public List<BookDto> getBooksListByAuthorName(String name) {
        return bookRepository.findAllByAuthor_Name(name).stream().map(bookConverter::convertToDto).collect(Collectors.toList());
    }

    public List<BookDto> getBooksListByAuthorId(int id) {
        return bookRepository.findAllByAuthor_Id(id).stream().map(bookConverter::convertToDto).collect(Collectors.toList());
    }

    public BookDto getBookById(int id) {
       return bookConverter.convertToDto(bookRepository.findById(id));
    }

    public List<CommentsDto> getCommentsForBook(int id) {
        return commentsRepository.findAllByBook_Id(id).stream().map(commentsConverter::convertToDto).collect(Collectors.toList());
    }

    public void createComment(int id, CommentsDto commentDto) {
        CommentsDbo comment = commentsConverter.convertToDbo(commentDto);
        comment.setBook(bookRepository.findById(id));
        comment.setUser(usersRepository.findByLogin(commentDto.getUserName()));
        commentsRepository.save(comment);
    }
}
