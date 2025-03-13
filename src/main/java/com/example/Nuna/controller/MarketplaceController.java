package com.example.Nuna.controller;

import com.example.Nuna.model.Explorer;
import com.example.Nuna.model.Marketplace;
import com.example.Nuna.model.MarketListing;
import com.example.Nuna.service.MarketplaceService;
import com.example.Nuna.service.ExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    @Autowired
    private MarketplaceService marketplaceService;

    @Autowired
    private ExplorerService explorerService;

    @GetMapping ("")
    public String viewMarketplace(Model model) {
        Explorer explorer = getAuthenticatedExplorer();
        Marketplace marketplace = marketplaceService.getMarketplaceByExplorerId(explorer.getId());
        model.addAttribute("marketplace", marketplace);
        return "marketplace";
    }

    @PostMapping("/add")
    public String addToMarketplace(@RequestParam Long mapId, @RequestParam double price) {
        Explorer explorer = getAuthenticatedExplorer();
        marketplaceService.addListingToMarketplace(explorer.getId(), mapId, price);
        return "redirect:/marketplace";
    }

    @PostMapping("/remove/{listingId}")
    public String removeFromMarketplace(@PathVariable Long listingId) {
        Explorer explorer = getAuthenticatedExplorer();
        marketplaceService.removeListingFromMarketplace(explorer.getId(), listingId);
        return "redirect:/marketplace";
    }

    @PostMapping("/update")
    public String updateListingPrice(@RequestParam Long listingId, @RequestParam double newPrice) {
        marketplaceService.updateListingPrice(listingId, newPrice);
        return "redirect:/marketplace";
    }

    @PostMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        String username = authentication.getName();
        Explorer explorer = explorerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Explorer not found"));
        Marketplace marketplace = marketplaceService.getMarketplaceByExplorerId(explorer.getId());

        model.addAttribute("explorer", explorer);
        model.addAttribute("marketplace", marketplace);
        model.addAttribute("totalPrice", marketplace.getListings().stream().mapToDouble(MarketListing::getPrice).sum());

        return "checkout-receipt"; // Displays marketplace summary
    }

    private Explorer getAuthenticatedExplorer() {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return explorerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Explorer not found"));
    }
}

