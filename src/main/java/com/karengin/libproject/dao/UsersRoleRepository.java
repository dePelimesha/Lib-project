package com.karengin.libproject.dao;

import com.karengin.libproject.dbo.UsersRoleDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UsersRoleRepository extends JpaRepository<UsersRoleDbo, Long> {
    UsersRoleDbo findById(int id);
}
