package com.karengin.libproject.dao;

import com.karengin.libproject.dbo.BookDbo;
import com.karengin.libproject.dbo.CommentsDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsDbo, Long> {
    List<CommentsDbo> findAllByBook_Id(int id);
}

