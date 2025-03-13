package com.example.Nuna.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;


@Entity
public class MarketListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "marketplace_id", nullable = false)
    private Marketplace marketplace;

    @ManyToOne
    @JoinColumn(name = "map_id", nullable = false)
    private Map map;

    private double price;

    //Empty constructor
    public MarketListing() {}

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    //Method to check if listing is free

    public boolean isFree() {
        return price == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketListing that = (MarketListing) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MarketListing{" +
                "id=" + id +
                ", marketplace=" + (marketplace !=null ? marketplace.getId() : "null") +
                ", map=" + (map !=null ? map.getId() : "null") +
                ", price=" + price +
                '}';
    }
}
