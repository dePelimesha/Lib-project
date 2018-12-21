package com.karengin.libproject.converter;

import com.karengin.libproject.dbo.AuthorDbo;
import com.karengin.libproject.dto.AuthorDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthorConverter implements DtoDboConverter<AuthorDto, AuthorDbo> {

    @Override
    public AuthorDto convertToDto(AuthorDbo dbo) {
        final AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(dbo,authorDto);
        return authorDto;
    }

    @Override
    public AuthorDbo convertToDbo(AuthorDto dto) {
        final AuthorDbo authorDbo = new AuthorDbo();
        BeanUtils.copyProperties(dto,authorDbo);
        return authorDbo;
    }
}
