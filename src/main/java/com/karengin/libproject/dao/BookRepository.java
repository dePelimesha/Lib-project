package com.karengin.libproject.dao;

import com.karengin.libproject.dbo.AuthorDbo;
import com.karengin.libproject.dbo.BookDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookDbo, Long> {
    List<BookDbo> findAllByAuthor_Name(String name);
    List<BookDbo> findAllByAuthor_Id(int id);
    BookDbo findById(int id);
}
