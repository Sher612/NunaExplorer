package com.example.Nuna.controller;

import com.example.Nuna.model.Genre;
import jakarta.validation.Valid;
import com.example.Nuna.model.Explorer;
import com.example.Nuna.service.ExplorerService;
import com.example.Nuna.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ExplorerService explorerService;

    @Autowired
    private MapService mapService;

    //Show the home page
    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    //Show the login page
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    //Show the registration page
    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("explorer", new Explorer()); //Initialize an empty Explorer object for the form
        return "register";
    }

    //Handle user registration
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("explorer")Explorer explorer, BindingResult result){
        if (result.hasErrors()){
            return "register"; //return to the registration form if there are validation errors
        }
        Optional<Explorer> existingExplorer = explorerService.findByUsername(explorer.getUsername());
        if (existingExplorer.isPresent()) {
            result.rejectValue("username", "error.explorer", "An account with this username already exists.");
            return "register";
        }

        explorer.setPassword(new BCryptPasswordEncoder().encode(explorer.getPassword()));
        explorerService.registerExplorer(explorer);
        return "redirect:/login";
    }

//    //Display maps page with filtering by Genre
//    @GetMapping("/explore/maps")
//    public String getMapsPage(@RequestParam(value= "genre", required=false) String genre, Model model){
//        if (genre != null){
//            try {
//                Genre genreEnum = Genre.valueOf(genre.toUpperCase()); //Convert String to Enum//
//                model.addAttribute("maps", mapService.getMapsByGenre(genreEnum));
//        } catch (IllegalArgumentException e) {
//            //Handle invalid genre case (e.g. show all maps instead)
//                model.addAttribute("maps", mapService.getAllMaps());
//            }
//        } else {
//            model.addAttribute("maps", mapService.getAllMaps());
//        }
//        return "maps";
//    }
}
