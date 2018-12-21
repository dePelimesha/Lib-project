package com.karengin.libproject.dao;

import com.karengin.libproject.dbo.AuthorDbo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorDbo, Long> {
    AuthorDbo getByName(String name);
}
