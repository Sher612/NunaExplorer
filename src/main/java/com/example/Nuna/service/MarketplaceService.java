package com.example.Nuna.service;

import com.example.Nuna.model.*;
import com.example.Nuna.repository.ExplorerRepository;
import com.example.Nuna.repository.MapRepository;
import com.example.Nuna.repository.MarketListingRepository;
import com.example.Nuna.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MarketplaceService {

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private ExplorerRepository explorerRepository;

    @Autowired
    private MarketListingRepository marketListingRepository;

    public Marketplace getMarketplaceByExplorerId(Long explorerId){
        Explorer explorer = explorerRepository.findById(explorerId)
                .orElseThrow( () -> new IllegalArgumentException("Explorer not found"));

        return marketplaceRepository.findByExplorer(explorer)
                .orElseGet(() -> {
                    Marketplace newMarketplace = new Marketplace();
                    newMarketplace.setExplorer(explorer);
                    return marketplaceRepository.save(newMarketplace);
                });
    }

    public Marketplace addListingToMarketplace(Long explorerId, Long mapId, double price){
        Marketplace marketplace = getMarketplaceByExplorerId(explorerId);
        Map map = mapRepository.findById(mapId)
                .orElseThrow( () -> new RuntimeException("Map not found"));

        Optional<MarketListing> existingListing = marketplace.getListings().stream()
                .filter(item -> item.getMap().getId().equals(mapId))
                .findFirst();

        if (existingListing.isEmpty()) {
            MarketListing newListing = new MarketListing();
            newListing.setMap(map);
            newListing.setPrice(price);
            newListing.setMarketplace(marketplace);

            //save new listing before adding to marketplace
            marketListingRepository.save(newListing);
            marketplace.getListings().add(newListing);
        }

        return marketplaceRepository.save(marketplace);
    }

    public Marketplace removeListingFromMarketplace(Long explorerId, Long mapId){
        Marketplace marketplace = getMarketplaceByExplorerId(explorerId);

        Optional<MarketListing> listingToRemove = marketplace.getListings().stream()
                .filter(item -> item.getMap().getId().equals(mapId))
                .findFirst();

        listingToRemove.ifPresent(listing -> {
            marketplace.getListings().remove(listing);
            marketListingRepository.delete(listing);
        });

        return marketplaceRepository.save(marketplace);
    }

    public void updateListingStatus(Long listingId, String newStatus){
        MarketListing listing = marketListingRepository.findById(listingId)
                .orElseThrow( () -> new RuntimeException("Market listing not found"));

        try {
            MapStatus statusEnum = MapStatus.valueOf(newStatus.toUpperCase());//converts string value into MapStatus enum
            listing.getMap().setStatus(statusEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + newStatus);
        }
        mapRepository.save(listing.getMap());
    }

    @Transactional
    public void updateListingPrice(Long listingId, double newPrice) {
        MarketListing listing = marketListingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        listing.setPrice(newPrice);
        marketListingRepository.save(listing);
    }
}
