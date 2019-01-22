package com.karengin.libproject.service;


import com.karengin.libproject.Entity.BookEntity;
import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.converter.AbstractConverter;
import com.karengin.libproject.converter.GenreConverter;
import com.karengin.libproject.repository.GenreRepository;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.converter.GenreConverter;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.repository.GenreRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService extends AbstractService<GenreDto, GenreEntity, GenreRepository> {

    public GenreService(GenreRepository repository, AbstractConverter<GenreDto, GenreEntity> converter) {
        super(repository, converter);
    }

    public ResponseEntity<List<GenreDto>> getGenresList() {
        return ResponseEntity.status(200).body(
                getRepository().findAll().stream().map(getConverter()::convertToDto)
                        .collect(Collectors.toList()));
    }

    public ResponseEntity<GenreDto> getGenreById(final long id) {
        if(getRepository().findById(id) != null) {
            return ResponseEntity.status(200).body(
                    getConverter().convertToDto(getRepository().findById(id)));
        }
        return ResponseEntity.status(400).body(null);
    }

    @Override
    protected boolean beforeSave(GenreDto dto) {
        return false;
    }

    @Override
    public List<GenreDto> findByFilterParameter(String filterParameter) {
        return null;
    }
}