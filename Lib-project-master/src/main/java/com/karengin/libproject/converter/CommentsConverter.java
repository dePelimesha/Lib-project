package com.karengin.libproject.converter;

import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsConverter implements DtoEntityConverter<CommentsDto, CommentsEntity> {

    private final UsersRepository usersRepository;

    @Override
    public CommentsDto convertToDto(final CommentsEntity commentEntity) {
        final CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(commentEntity.getId());
        commentsDto.setComment(commentEntity.getComment());
        commentsDto.setUserName(commentEntity.getUser().getLogin());
        return commentsDto;
    }

    @Override
    public CommentsEntity convertToEntity(final CommentsDto dto) {
        final CommentsEntity commentEntity = new CommentsEntity();
        BeanUtils.copyProperties(dto, commentEntity);
        commentEntity.setUser(usersRepository.findByLogin(dto.getUserName()));
        return commentEntity;
    }
}
