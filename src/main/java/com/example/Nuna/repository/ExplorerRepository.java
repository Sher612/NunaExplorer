package com.example.Nuna.repository;

import com.example.Nuna.model.Explorer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExplorerRepository extends JpaRepository<Explorer, Long> {
    Optional<Explorer> findByUsername(String username);
}
