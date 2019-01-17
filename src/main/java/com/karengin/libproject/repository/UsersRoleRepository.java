package com.karengin.libproject.repository;

import com.karengin.libproject.Entity.UsersRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UsersRoleRepository extends JpaRepository<UsersRoleEntity, Long> {
    UsersRoleEntity findById(long id);
    UsersRoleEntity findByRole(String role);
}
