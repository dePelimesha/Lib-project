package com.karengin.libproject.repository;

import com.karengin.libproject.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    UsersEntity findByLogin(String login);
    List<UsersEntity> findAllByLoginContains(String string);
    long countByLoginContains(String string);
}
