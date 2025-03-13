package com.example.Nuna.controller;


import com.example.Nuna.model.Explorer;
import com.example.Nuna.model.Genre;
import com.example.Nuna.model.Map;
import com.example.Nuna.service.ExplorerService;
import com.example.Nuna.service.MapService;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/maps")
public class MapsController {

    @Autowired
    private MapService mapService;

    @Autowired
    private ExplorerService explorerService;

    @GetMapping("")
    public String getAllMaps(@RequestParam(value = "genre", required = false) String genre, Model model) {
        if (genre != null) {
            try {
                Genre genreEnum = Genre.valueOf(genre.toUpperCase());
                model.addAttribute("maps", mapService.getMapsByGenre(genreEnum));
            } catch (IllegalArgumentException e) {
                model.addAttribute("maps", mapService.getAllMaps());
            }
        } else {
            model.addAttribute("maps", mapService.getAllMaps());
        }
        return "map-list";  // Ensure this is the correct Thymeleaf file name
    }

    @GetMapping("/new")
    public String showCreateMapForm(Model model) {
        Map map = new Map();
        model.addAttribute("map", map);
        model.addAttribute("genres", Genre.values()); //Populate drop down for Genres
        return "map-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditMapForm(@PathVariable Long id, Model model) {
        Optional<Map> optionalMap = mapService.getMapById(id);
        if(optionalMap.isPresent()) {
            model.addAttribute("map", optionalMap.get());
            model.addAttribute("genres", Genre.values());
            return "map-form";
        } else {
            return "redirect:/maps";
        }
    }

    @PostMapping("/save")
    public String saveMap(@ModelAttribute("map") Map map, Authentication authentication) {
        // Get the authenticated user's username
        String username = authentication.getName();

        // Find the Explorer based on username
        Explorer explorer = explorerService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Explorer not found"));

        // Set the Explorer as the creator of the map
        map.setExplorer(explorer);

        // Save the map
        if (map.getId() != null) {
            mapService.updateMap(map.getId(), map);
        } else {
            mapService.createMap(map);
        }

        return "redirect:/maps";
    }

    @GetMapping("/delete/{id}")
    public String deleteMap(@PathVariable Long id) {
        mapService.deleteMapById(id);
        return "redirect:/maps";
    }
}
