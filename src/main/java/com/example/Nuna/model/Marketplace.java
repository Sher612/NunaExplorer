package com.example.Nuna.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Marketplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "explorer_id", nullable = false)
    private Explorer explorer;

    @OneToMany(mappedBy = "marketplace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MarketListing> listings = new HashSet<>(); //initialize to avoid null issues

    //Empty Constructor
    public Marketplace() {}

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public Set<MarketListing> getListings() {
        return listings;
    }

    public void setListings(Set<MarketListing> listings) {
        this.listings = listings;
    }

    //Method to get free maps safely
    public List<MarketListing> getFreeMaps() {
        return listings !=null
                ? listings.stream()
                .filter(MarketListing::isFree)
                .toList()
                : List.of(); //returns an empty list if null
    }
}
