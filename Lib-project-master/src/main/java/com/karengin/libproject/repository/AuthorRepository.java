package com.karengin.libproject.repository;

import com.karengin.libproject.Entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    AuthorEntity getByName(String name);
    AuthorEntity findById(long id);
    boolean existsByName(String name);
    AuthorEntity findByName(String name);
}
