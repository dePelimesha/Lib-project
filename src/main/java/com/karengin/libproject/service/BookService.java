package com.karengin.libproject.service;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.converter.BookConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.converter.GenreConverter;
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
    private final GenreConverter genreConverter;

    public ResponseEntity<String> createBook(final BookDto bookDto) {
        if(bookDto.getAuthor() == null) {
            bookDto.setAuthor("No author");
        } else if (authorRepository.getByName(bookDto.getAuthor()) == null) {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setName(bookDto.getAuthor());
            authorRepository.save(authorEntity);
        }

        bookDto.getGenres().forEach(genre -> {
            if (genreRepository.getByGenre(genre) == null) {
                GenreEntity newGenre = new GenreEntity();
                newGenre.setGenre(genre);
                genreRepository.save(newGenre);
            }
        });

        bookRepository.save(bookConverter.convertToEntity(bookDto));
        return ResponseEntity.status(201).body("Book was added");
    }

    public ResponseEntity<List<BookDto>> getBooksList(final String[] genres) {
        if (genres != null) {
            List<BookDto> selected = genreRepository.getByGenre(genres[0]).getBooks()
                    .stream().map(bookConverter::convertToDto).collect(Collectors.toList());

            for (int i = 1; i < genres.length; i++) {
                final GenreEntity genre = genreRepository.getByGenre(genres[i]);
                final List<BookDto> afterDelete = new ArrayList<>(selected);
                selected.forEach(bookDto -> {
                    if (!bookDto.getGenres().contains(genre.getGenre())) {
                        afterDelete.remove(bookDto);
                    }
                });
                selected = new ArrayList<>(afterDelete);
            }

            return ResponseEntity.status(200).body(selected);
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
        final List<BookEntity> bookEntities = bookRepository.findAllByTitleStartsWith(title);
        if(bookEntities.isEmpty()) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(
                bookEntities.stream().map(bookConverter::convertToDto).collect(Collectors.toList())
        );
    }

    public ResponseEntity<Long> getBooksCount(final String title) {
        if (title == null) {
            return ResponseEntity.status(200).body(bookRepository.count());
        }
        return ResponseEntity.status(200).body(bookRepository.countByTitleStartsWith(title));
    }

    public ResponseEntity<String> changeBook(final BookDto bookDto) {
        BookEntity changedBook = bookRepository.findById(bookDto.getId());
        if (changedBook != null) {
            changedBook.setTitle(bookDto.getTitle());
            changedBook.setDescription(bookDto.getDescription());

            if(bookDto.getAuthor().equals("")) {
                changedBook.setAuthor(authorRepository.getByName("No author"));
            } else if (authorRepository.getByName(bookDto.getAuthor()) == null) {
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setName(bookDto.getAuthor());
                authorRepository.save(authorEntity);
                changedBook.setAuthor(authorEntity);
            } else {
                changedBook.setAuthor(authorRepository.getByName(bookDto.getAuthor()));
            }

            final List<GenreEntity> genreEntities = new ArrayList<>();

            bookDto.getGenres().forEach(genre -> {
                GenreEntity currGenre = genreRepository.getByGenre(genre);
                if (currGenre == null) {
                    currGenre = new GenreEntity();
                    currGenre.setGenre(genre);
                    genreRepository.save(currGenre);
                }
                genreEntities.add(currGenre);
            });

            changedBook.setGenresList(genreEntities);
            bookRepository.save(changedBook);
            return ResponseEntity.status(201).body("Book was changed");
        }
        return ResponseEntity.status(400).body("No such book");
    }

    public ResponseEntity<String> removeBook(final BookDto bookDto) {
        bookRepository.delete(bookConverter.convertToEntity(bookDto));
        return ResponseEntity.status(200).body("Book was deleted");
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

    public ResponseEntity<String> changeBookNew(final long id, final BookDto bookDto) {
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
