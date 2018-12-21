package com.karengin.libproject.converter;

import com.karengin.libproject.dao.BookRepository;
import com.karengin.libproject.dao.UsersRepository;
import com.karengin.libproject.dbo.CommentsDbo;
import com.karengin.libproject.dbo.UsersDbo;
import com.karengin.libproject.dto.CommentsDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentsConverter implements DtoDboConverter<CommentsDto, CommentsDbo> {

    @Override
    public CommentsDto convertToDto(CommentsDbo dbo) {
        final CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(dbo.getId());
        commentsDto.setComment(dbo.getComment());
        commentsDto.setUserName(dbo.getUser().getLogin());
        return commentsDto;
    }

    @Override
    public CommentsDbo convertToDbo(CommentsDto dto) {
        final CommentsDbo commentsDbo = new CommentsDbo();
        BeanUtils.copyProperties(dto,commentsDbo);
        return commentsDbo;
    }
}
