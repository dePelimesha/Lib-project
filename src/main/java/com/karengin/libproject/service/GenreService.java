package com.karengin.libproject.service;

import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.converter.GenreConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.repository.AuthorRepository;
import com.karengin.libproject.repository.GenreRepository;
import com.karengin.libproject.repository.CommentsRepository;
import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.dto.CommentsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreConverter genreConverter;

    public ResponseEntity<List<GenreDto>> getGenresList() {
        return ResponseEntity.status(200).body(
                genreRepository.findAll().stream().map(genreConverter::convertToDto)
                        .collect(Collectors.toList()));
    }

    public ResponseEntity<GenreDto> getGenreById(final long id) {
        if(genreRepository.findById(id) != null) {
            return ResponseEntity.status(200).body(
                    genreConverter.convertToDto(genreRepository.findById(id)));
        }
        return ResponseEntity.status(400).body(null);
    }

}