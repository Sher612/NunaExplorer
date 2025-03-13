package com.example.Nuna.service;

import com.example.Nuna.model.Explorer;
import com.example.Nuna.model.Genre;
import com.example.Nuna.model.Map;
import com.example.Nuna.model.Marketplace;
import com.example.Nuna.repository.ExplorerRepository;
import com.example.Nuna.repository.MapRepository;
import com.example.Nuna.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExplorerService {

    @Autowired
    private ExplorerRepository explorerRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Explorer registerExplorer(Explorer explorer) {
        //encode the password before saving
        explorer.setPassword(passwordEncoder.encode(explorer.getPassword()));

        Explorer savedExplorer = explorerRepository.save(explorer);

        //Create and save a new Marketplace for the registered Explorer
        Marketplace marketplace = new Marketplace(); //ensure constructor is empty
        marketplace.setExplorer(savedExplorer);
        marketplaceRepository.save(marketplace);

        return savedExplorer;
    }

    public List<Map> getMapsByGenre(Genre genre) {
        return mapRepository.findByGenre(genre);
    }

    public Optional<Explorer> findByUsername(String username) {
        return explorerRepository.findByUsername(username);
    }

    public List<Map> getAllMaps() {
        return mapRepository.findAll();
    }
}
