package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.repository.UsersRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserConverter implements DtoEntityConverter<UsersDto, UsersEntity> {

    private UsersRoleRepository usersRoleRepository;

    @Override
    public UsersDto convertToDto(final UsersEntity userEntity) {
        final UsersDto userDto = new UsersDto();
        BeanUtils.copyProperties(userEntity,userDto);
        userDto.setRole(userEntity.getUserRole().getRole());
        return userDto;
    }

    @Override
    public UsersEntity convertToEntity(final UsersDto dto) {
        final UsersEntity userEntity = new UsersEntity();
        BeanUtils.copyProperties(dto,userEntity);
        userEntity.setUserRole(usersRoleRepository.findByRole(dto.getRole()));
        return userEntity;
    }
}
