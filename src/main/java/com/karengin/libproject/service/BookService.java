package com.karengin.libproject.service;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.converter.BookConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.BookRepository;
import com.karengin.libproject.repository.CommentsRepository;
import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final CommentsRepository commentsRepository;
    private final CommentsConverter commentsConverter;
    private final AuthorRepository authorRepository;

    public ResponseEntity<String> createBook(final BookDto bookDto) {
        if(bookDto.getAuthor() == null) {
            bookDto.setAuthor("No author");
        } else if (authorRepository.getByName(bookDto.getAuthor()) == null) {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setName(bookDto.getAuthor());
            authorRepository.save(authorEntity);
        }
        bookRepository.save(bookConverter.convertToEntity(bookDto));
        return ResponseEntity.status(201).body("Book was added");
    }

    public ResponseEntity<List<BookDto>> getBooksList() {
        return ResponseEntity.status(200).body(
                bookRepository.findAll().stream().map(bookConverter::convertToDto)
                        .collect(Collectors.toList()));
    }

    public ResponseEntity<List<BookDto>> getBooksListByAuthorId(final long id) {
        if(authorRepository.findById(id) != null) {
            return ResponseEntity.status(200).body(
                    bookRepository.findAllByAuthor_Id(id).stream().map(bookConverter::convertToDto)
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<BookDto> getBookById(final long id) {
        if(bookRepository.findById(id) != null) {
            return ResponseEntity.status(200).body(
                    bookConverter.convertToDto(bookRepository.findById(id)));
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<BookDto> getBookByName(final String name) {
        if(bookRepository.findByTitle(name) != null) {
            return ResponseEntity.status(200).body(
                    bookConverter.convertToDto(bookRepository.findByTitle(name)));
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<List<CommentsDto>> getCommentsForBook(final long id) {
        if(bookRepository.findById(id) != null) {
            return ResponseEntity.status(200).body(
                    commentsRepository.findAllByBookId(id).stream().map(commentsConverter::convertToDto)
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.status(400).body(null);
    }

    /*author Stanislav Patskevich */
    public ResponseEntity<String> deleteBook(final long id) {
        if(bookRepository.existsById(id)) {
            BookEntity book = bookRepository.findById(id);
            List<CommentsEntity> commentsList = book.getComments();
            for (CommentsEntity comment : commentsList) {
                commentsRepository.delete(comment);
            }
            bookRepository.delete(book);
            return ResponseEntity.status(200).body("Была удалена книга с ID №"+id);
        } else return ResponseEntity.status(404).body("Книга с ID №"+id+" не была найдена!");
    }

    public ResponseEntity<String> changeBook(final long id, final BookDto bookDto) {
        if(bookRepository.existsById(id)) {
            BookEntity book = bookRepository.findById(id);
            book.setTitle(bookDto.getTitle());
            book.setDescription(bookDto.getDescription());
            if (authorRepository.existsByName(bookDto.getAuthor())){
                book.setAuthor(authorRepository.findByName(bookDto.getAuthor()));
            } else book.setAuthor(authorRepository.findById(1));
            bookRepository.save(book);
            return ResponseEntity.status(200).body("Книга с ID №"+id+" была изменена!");
        } else return ResponseEntity.status(404).body("Книга с ID №"+id+" не была найдена!");
    }
}
