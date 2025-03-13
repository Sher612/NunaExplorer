package com.example.Nuna.repository;

import com.example.Nuna.model.BaseUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByUsername(String username);
}
