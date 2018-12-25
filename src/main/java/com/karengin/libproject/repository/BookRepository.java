package com.karengin.libproject.repository;

import com.karengin.libproject.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByAuthor_Id(long id);
    BookEntity findById(long id);
}
