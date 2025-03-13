package com.example.Nuna.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Explorer")
public class Explorer extends BaseUser{

    @OneToMany(mappedBy = "explorer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Map> collection = new HashSet<>();

    public Explorer() {
        super();
        setRole(Role.EXPLORER);
    }

    public Set<Map> getCollection() {
        return collection;
    }

    public void setCollection(Set<Map> collection) {
        this.collection = collection;
    }
}
