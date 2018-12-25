package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.AuthorEntity;
import com.karengin.libproject.dto.AuthorDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthorConverter implements DtoEntityConverter<AuthorDto, AuthorEntity> {

    @Override
    public AuthorDto convertToDto(final AuthorEntity authorEntity) {
        final AuthorDto authorDto = new AuthorDto();
        authorDto.setId(authorEntity.getId());
        authorDto.setName(authorEntity.getName());
        return authorDto;
    }

    @Override
    public AuthorEntity convertToEntity(final AuthorDto dto) {
        final AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(dto.getName());
        return authorEntity;
    }
}
