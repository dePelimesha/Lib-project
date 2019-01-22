package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.AbstractEntity;
import com.karengin.libproject.dto.AbstractDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractConverter<T extends AbstractDto, B extends AbstractEntity> {
    public abstract T convertToDto(final B entity);
    public abstract B convertToEntity(final T dto);

    public List<T> convertToDto(final Collection<B> entityCollection){
        if (entityCollection != null){
            List<T> list = new ArrayList<>();
            for (B e : entityCollection) {
                T convertToDto = convertToDto(e);
                list.add(convertToDto);
            }
            return list;
        }
        return null;
    }
}
