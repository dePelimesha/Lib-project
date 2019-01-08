package com.karengin.libproject.service;

import com.karengin.libproject.converter.GenreConverter;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.repository.GenreRepository;
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

}
