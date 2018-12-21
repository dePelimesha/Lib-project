package com.karengin.libproject.service;

import com.karengin.libproject.dao.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(final CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void deleteById(long id) {
        commentsRepository.existsById(id);
    }
}
