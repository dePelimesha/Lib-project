package com.karengin.libproject.service;

import com.karengin.libproject.Entity.CommentsEntity;
import com.karengin.libproject.converter.CommentsConverter;
import com.karengin.libproject.dto.CommentsDto;
import com.karengin.libproject.repository.BookRepository;
import com.karengin.libproject.repository.CommentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final CommentsConverter commentsConverter;
    private final BookRepository bookRepository;

    public ResponseEntity<String> deleteById(long id) {
        if(commentsRepository.findById(id) != null) {
            commentsRepository.deleteById(id);
            return ResponseEntity.status(200).body("Comment was deleted");
        }
        return ResponseEntity.status(400).body("Comment doesn't exist");
    }

    public ResponseEntity<String> createComment(long id, CommentsDto commentDto) {
        CommentsEntity comment = commentsConverter.convertToEntity(commentDto);
        comment.setBook(bookRepository.findById(id));
        commentsRepository.save(comment);
        return ResponseEntity.status(201).body("Comment was added");
    }
}
