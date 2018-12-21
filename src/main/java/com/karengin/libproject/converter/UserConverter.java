package com.karengin.libproject.converter;

import com.karengin.libproject.dbo.UsersDbo;
import com.karengin.libproject.dto.UsersDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserConverter implements DtoDboConverter<UsersDto, UsersDbo>{

    @Override
    public UsersDto convertToDto(UsersDbo dbo) {
        final UsersDto userDto = new UsersDto();
        BeanUtils.copyProperties(dbo,userDto);
        return userDto;
    }

    @Override
    public UsersDbo convertToDbo(UsersDto dto) {
        final UsersDbo userDbo = new UsersDbo();
        BeanUtils.copyProperties(dto,userDbo);
        return userDbo;
    }
}
