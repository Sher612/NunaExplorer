package com.example.Nuna.repository;

import com.example.Nuna.model.Explorer;
import com.example.Nuna.model.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
    Optional<Marketplace> findByExplorer(Explorer explorer);
}
