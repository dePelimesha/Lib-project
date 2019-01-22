package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.UI.view.BooksView;
import com.karengin.libproject.converter.AbstractConverter;
import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService extends AbstractService<AuthorDto, AuthorEntity, AuthorRepository> {

    @Autowired
    private BookService bookService;

    public AuthorService(AuthorRepository repository, AbstractConverter<AuthorDto, AuthorEntity> converter) {
        super(repository, converter);
    }

    public ResponseEntity<List<AuthorDto>> getAuthorsList() {
        return ResponseEntity.status(200).body(
                getRepository().findAll().stream().map(getConverter()::convertToDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<String> save(AuthorDto authorDto) {
        if (getRepository().getByName(authorDto.getName()) == null) {
            getRepository().save(getConverter().convertToEntity(authorDto));
            return ResponseEntity.status(201).body("Author was added");
        }
        return ResponseEntity.status(403).body("Author already exists");
    }

    public ResponseEntity<Long> getAuthorsCount() {
        return ResponseEntity.status(200).body(getRepository().count());
    }

    /*author Stanislav Patskevich */
    public ResponseEntity<String> deleteAuthor(final long id) {
        if (getRepository().existsById(id)) {
            AuthorEntity author = getRepository().findById(id);
            List<BookEntity> listBook = author.getBooks();
            for (BookEntity book : listBook) {
                book.setAuthor(getRepository().findById(1));
                bookService.getRepository().save(book);
            }
            getRepository().delete(author);
            return ResponseEntity.status(200).body("Автор с ID № "+id+" был удалён");
        }
        return ResponseEntity.status(404).body("Автора с ID № "+id+" не существует");
    }

    public ResponseEntity<String> сhangeAuthor(final long id, final String new_name) {
        if (getRepository().existsById(id)) {
            AuthorEntity author = getRepository().findById(id);
            author.setName(new_name);
            getRepository().save(author);
            return ResponseEntity.status(200).body("Автор с ID № "+id+" был изменён");
        }
        return ResponseEntity.status(404).body("Автора с ID № "+id+" не существует");
    }

    @Override
    protected boolean beforeSave(AuthorDto dto) {
        return false;
    }

    @Override
    public List<AuthorDto> findByFilterParameter(String filterParameter) {
        return null;
    }
}
