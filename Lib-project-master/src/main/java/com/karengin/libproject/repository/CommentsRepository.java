package com.karengin.libproject.repository;

import com.karengin.libproject.Entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    List<CommentsEntity> findAllByBookId(long id);
    CommentsEntity findById(long id);
}

