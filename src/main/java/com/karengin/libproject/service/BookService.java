package com.karengin.libproject.service;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.converter.BookConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.BookRepository;
import com.karengin.libproject.repository.CommentsRepository;
import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.dto.BookDto;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final GenreRepository genreRepository;

    public ResponseEntity<String> createBook(final BookDto bookDto) {
        if(bookDto.getAuthor() == null) {
            bookDto.setAuthor("No author");
        } else if (authorRepository.getByName(bookDto.getAuthor()) == null) {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setName(bookDto.getAuthor());
            authorRepository.save(authorEntity);
        }

        final boolean[] exists = {true};

        bookDto.getGenres().forEach(genre -> {
            if (genreRepository.getByGenre(genre) == null) {
                exists[0] = false;
            }
        });

        if(exists[0]) {
            bookRepository.save(bookConverter.convertToEntity(bookDto));
            return ResponseEntity.status(201).body("Book was added");
        }

        return ResponseEntity.status(400).body("No such genre");
    }

    public ResponseEntity<List<BookDto>> getBooksList(final String[] genres) {
        if (genres != null) {
            List<BookEntity> selected = genreRepository.getByGenre(genres[0]).getBooks();

            for (int i = 1; i < genres.length; i++) {
                final GenreEntity genre = genreRepository.getByGenre(genres[i]);
                final List<BookEntity> afterDelete = new ArrayList<>(selected);
                selected.forEach(bookEntity -> {
                    if (!bookEntity.getGenresList().contains(genre)) {
                        afterDelete.remove(bookEntity);
                    }
                });
                selected = new ArrayList<>(afterDelete);
            }

            return ResponseEntity.status(200).body(
                    selected.stream().map(bookConverter::convertToDto).collect(Collectors.toList()));
        }

        return ResponseEntity.status(200).body(
                bookRepository.findAll().stream()
                        .map(bookConverter::convertToDto).collect(Collectors.toList()));
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

    public ResponseEntity<List<CommentsDto>> getCommentsForBook(final long id) {
        if(bookRepository.findById(id) != null) {
            return ResponseEntity.status(200).body(
                    commentsRepository.findAllByBookId(id).stream().map(commentsConverter::convertToDto)
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.status(400).body(null);
    }
  
    public ResponseEntity<List<BookDto>> getBooksByTitle(final String title) {
        final List<BookEntity> bookEntities = bookRepository.findAllByTitleContains(title);
        if(bookEntities.isEmpty()) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(
                bookEntities.stream().map(bookConverter::convertToDto).collect(Collectors.toList())
        );
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
        } else return ResponseEntity.status(400).body("Книга с ID №"+id+" не была найдена!");
    }

    public ResponseEntity<String> changeBook(final long id, final BookDto bookDto) {
        if(bookRepository.existsById(id)) {
            BookEntity book = bookRepository.findById(id);
            book.setTitle(bookDto.getTitle());
            book.setDescription(bookDto.getDescription());
            if (authorRepository.existsByName(bookDto.getAuthor())){
                book.setAuthor(authorRepository.findByName(bookDto.getAuthor()));
            } else {
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setName(bookDto.getAuthor());
                authorRepository.save(authorEntity);
                book.setAuthor(authorEntity);
            }
            bookRepository.save(book);
            return ResponseEntity.status(200).body("Книга с ID №"+id+" была изменена!");
        } else return ResponseEntity.status(400).body("Книга с ID №"+id+" не была найдена!");
    }
}
