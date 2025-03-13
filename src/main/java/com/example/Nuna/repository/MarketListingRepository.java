package com.example.Nuna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Nuna.model.MarketListing;

public interface MarketListingRepository extends JpaRepository<MarketListing, Long> {
}
