package com.karengin.libproject.repository;

import com.karengin.libproject.Entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity getByGenre(String genre);
}
