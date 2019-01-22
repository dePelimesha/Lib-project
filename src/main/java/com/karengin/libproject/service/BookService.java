package com.karengin.libproject.service;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.converter.AbstractConverter;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService extends AbstractService<BookDto, BookEntity, BookRepository> {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CommentsService commentsService;

    public BookService(BookRepository repository, AbstractConverter<BookDto, BookEntity> converter) {
        super(repository, converter);
    }

    @Override
    public ResponseEntity<String> save(final BookDto bookDto) {
        if(bookDto.getAuthor() == null) {
            bookDto.setAuthor("No author");
        } else if (authorService.getRepository().getByName(bookDto.getAuthor()) == null) {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setName(bookDto.getAuthor());
            authorService.getRepository().save(authorEntity);
        }

        bookDto.getGenres().forEach(genre -> {
            if (genreService.getRepository().getByGenre(genre) == null) {
                GenreEntity newGenre = new GenreEntity();
                newGenre.setGenre(genre);
                genreService.getRepository().save(newGenre);
            }
        });

        getRepository().save(getConverter().convertToEntity(bookDto));
        return ResponseEntity.status(201).body("Book was added");
    }

    public ResponseEntity<List<BookDto>> getBooksList(final String[] genres) {
        if (genres != null) {
            List<BookDto> selected = genreService.getRepository().getByGenre(genres[0]).getBooks()
                    .stream().map(getConverter()::convertToDto).collect(Collectors.toList());

            for (int i = 1; i < genres.length; i++) {
                final GenreEntity genre = genreService.getRepository().getByGenre(genres[i]);
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
                getRepository().findAll().stream()
                        .map(getConverter()::convertToDto).collect(Collectors.toList()));
    }

    public ResponseEntity<List<BookDto>> getBooksListByAuthorId(final long id) {
        if(authorService.getRepository().findById(id) != null) {
            return ResponseEntity.status(200).body(
                    getRepository().findAllByAuthor_Id(id).stream().map(getConverter()::convertToDto)
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<BookDto> getBookById(final long id) {
        if(getRepository().findById(id) != null) {
            return ResponseEntity.status(200).body(
                    getConverter().convertToDto(getRepository().findById(id)));
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<List<CommentsDto>> getCommentsForBook(final long id) {
        if(getRepository().findById(id) != null) {
            return ResponseEntity.status(200).body(
                    commentsService.getRepository().findAllByBookId(id).stream()
                            .map(commentsService.getConverter()::convertToDto)
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.status(400).body(null);
    }

    public ResponseEntity<List<BookDto>> getBooksByTitle(final String title) {
        final List<BookEntity> bookEntities = getRepository().findAllByTitleStartsWith(title);
        if(bookEntities.isEmpty()) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(
                bookEntities.stream().map(getConverter()::convertToDto).collect(Collectors.toList())
        );
    }

    public ResponseEntity<String> changeBook(final BookDto bookDto) {
        BookEntity changedBook = getRepository().findById(bookDto.getId()).get();
        if (changedBook != null) {
            changedBook.setTitle(bookDto.getTitle());
            changedBook.setDescription(bookDto.getDescription());

            if(bookDto.getAuthor().equals("")) {
                changedBook.setAuthor(authorService.getRepository().getByName("No author"));
            } else if (authorService.getRepository().getByName(bookDto.getAuthor()) == null) {
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setName(bookDto.getAuthor());
                authorService.getRepository().save(authorEntity);
                changedBook.setAuthor(authorEntity);
            } else {
                changedBook.setAuthor(authorService.getRepository().getByName(bookDto.getAuthor()));
            }

            final List<GenreEntity> genreEntities = new ArrayList<>();

            bookDto.getGenres().forEach(genre -> {
                GenreEntity currGenre = genreService.getRepository().getByGenre(genre);
                if (currGenre == null) {
                    currGenre = new GenreEntity();
                    currGenre.setGenre(genre);
                    genreService.getRepository().save(currGenre);
                }
                genreEntities.add(currGenre);
            });

            changedBook.setGenresList(genreEntities);
            getRepository().save(changedBook);
            return ResponseEntity.status(201).body("Book was changed");
        }
        return ResponseEntity.status(400).body("No such book");
    }

      /*author Stanislav Patskevich */

    public ResponseEntity<String> changeBookNew(final long id, final BookDto bookDto) {
        if(getRepository().existsById(id)) {
            BookEntity book = getRepository().findById(id);
            book.setTitle(bookDto.getTitle());
            book.setDescription(bookDto.getDescription());
            if (authorService.getRepository().existsByName(bookDto.getAuthor())){
                book.setAuthor(authorService.getRepository().findByName(bookDto.getAuthor()));
            } else {
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setName(bookDto.getAuthor());
                authorService.getRepository().save(authorEntity);
                book.setAuthor(authorEntity);
            }
            getRepository().save(book);
            return ResponseEntity.status(200).body("Книга с ID №"+id+" была изменена!");
        } else return ResponseEntity.status(400).body("Книга с ID №"+id+" не была найдена!");
    }

    @Override
    protected boolean beforeSave(BookDto dto) {
        return false;
    }

    @Override
    public List<BookDto> findByFilterParameter(String filterParameter) {
        return null;
    }
}
