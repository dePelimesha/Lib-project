package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.dto.GenreDto;
import com.karengin.libproject.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreConverter extends AbstractConverter<GenreDto, GenreEntity> {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto convertToDto(GenreEntity entity) {
        final GenreDto genreDto = new GenreDto();
        genreDto.setId(entity.getId());
        genreDto.setGenre(entity.getGenre());
        return genreDto;
    }

    @Override
    public GenreEntity convertToEntity(GenreDto dto) {
        final GenreEntity genreEntity = new GenreEntity();
        genreEntity.setGenre(dto.getGenre());
        return genreEntity;
    }

    public List<String> convertToDto(final List<GenreEntity> entity) {
        final List<String> genreDto = new ArrayList<>();
        entity.forEach(genre -> genreDto.add(genre.getGenre()));
        return genreDto;
    }

    public List<GenreEntity> convertToEntity(final List<String> dto) {
        final List<GenreEntity> genreEntity = new ArrayList<>();
        dto.forEach(genre -> genreEntity.add(genreRepository.getByGenre(genre)));
        return genreEntity;
    }
}
