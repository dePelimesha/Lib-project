package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.dto.UsersDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserConverter implements DtoEntityConverter<UsersDto, UsersEntity> {

    @Override
    public UsersDto convertToDto(final UsersEntity userEntity) {
        final UsersDto userDto = new UsersDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    @Override
    public UsersEntity convertToEntity(final UsersDto dto) {
        final UsersEntity userEntity = new UsersEntity();
        BeanUtils.copyProperties(dto,userEntity);
        return userEntity;
    }
}
