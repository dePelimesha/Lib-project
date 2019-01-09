package com.karengin.libproject.service;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.converter.AuthorConverter;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.dto.AuthorDto;
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

    public ResponseEntity<Long> getAuthorsCount() {
        return ResponseEntity.status(200).body(authorRepository.count());
    }
}
