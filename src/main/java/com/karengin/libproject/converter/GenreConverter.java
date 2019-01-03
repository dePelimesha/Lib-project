package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.GenreEntity;
import com.karengin.libproject.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreConverter implements DtoEntityConverter<GenreDto, GenreEntity>{
    @Override
    public GenreDto convertToDto(final GenreEntity entity){
        final GenreDto genreDto = new GenreDto();
        BeanUtils.copyProperties(entity, genreDto);
        return genreDto;
    }

    @Override
    public GenreEntity convertToEntity(final GenreDto dto){
        final GenreEntity genreEntity = new GenreEntity();
        BeanUtils.copyProperties(dto, genreEntity);
        return genreEntity;
    }
}