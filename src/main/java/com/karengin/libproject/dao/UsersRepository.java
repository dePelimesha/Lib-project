package com.karengin.libproject.dao;

import com.karengin.libproject.dbo.UsersDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UsersRepository extends JpaRepository<UsersDbo, Long> {
    UsersDbo findByLogin(String login);
}
