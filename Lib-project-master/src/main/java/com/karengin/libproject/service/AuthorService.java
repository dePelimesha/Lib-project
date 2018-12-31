package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.dto.AuthorDto;
import com.karengin.libproject.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private final BookRepository bookRepository;

    public ResponseEntity<List<AuthorDto>> getAuthorsList() {
        return ResponseEntity.status(200).body(
                authorRepository.findAll().stream().map(authorConverter::convertToDto)
                        .collect(Collectors.toList()));
    }

    public ResponseEntity<String> addAuthor(AuthorDto authorDto) {
        if ( authorRepository.getByName(authorDto.getName()) == null) {
            authorRepository.save(authorConverter.convertToEntity(authorDto));
            return ResponseEntity.status(201).body("Author was added");
        }
        return ResponseEntity.status(403).body("Author already exists");
    }

    /*author Stanislav Patskevich */
    public ResponseEntity<String> deleteAuthor(final long id) {
        if (authorRepository.existsById(id)) {
            AuthorEntity author = authorRepository.findById(id);
            List<BookEntity> listBook = author.getBooks();
            for (BookEntity book : listBook) {
                book.setAuthor(authorRepository.findById(1));
                bookRepository.save(book);
            }
            authorRepository.delete(author);
            return ResponseEntity.status(200).body("Автор с ID № "+id+" был удалён");
        }
        return ResponseEntity.status(404).body("Автора с ID № "+id+" не существует");
    }

    public ResponseEntity<String> сhangeAuthor(final long id, final String new_name) {
        if (authorRepository.existsById(id)) {
            AuthorEntity author = authorRepository.findById(id);
            author.setName(new_name);
            authorRepository.save(author);
            return ResponseEntity.status(200).body("Автор с ID № "+id+" был изменён");
        }
        return ResponseEntity.status(404).body("Автора с ID № "+id+" не существует");
    }
}
