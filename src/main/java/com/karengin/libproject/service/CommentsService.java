package com.karengin.libproject.service;

import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.converter.AbstractConverter;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.BookRepository;
import com.karengin.libproject.repository.CommentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentsService extends AbstractService<CommentsDto, CommentsEntity, CommentsRepository>{

    @Autowired
    private BookService bookService;

    public CommentsService(CommentsRepository repository, AbstractConverter<CommentsDto, CommentsEntity> converter) {
        super(repository, converter);
    }

    public ResponseEntity<String> deleteById(long id) {
        if(getRepository().findById(id) != null) {
            getRepository().deleteById(id);
            return ResponseEntity.status(200).body("Comment was deleted");
        }
        return ResponseEntity.status(400).body("Comment doesn't exist");
    }

    public ResponseEntity<String> createComment(long id, CommentsDto commentDto) {
        CommentsEntity comment = getConverter().convertToEntity(commentDto);
        comment.setBook(bookService.getRepository().findById(id));
        getRepository().save(comment);
        return ResponseEntity.status(201).body("Comment was added");
    }

    @Override
    protected boolean beforeSave(CommentsDto dto) {
        return false;
    }

    @Override
    public List<CommentsDto> findByFilterParameter(String filterParameter) {
        return null;
    }
}
