package com.example.Nuna.repository;

import com.example.Nuna.model.Genre;
import com.example.Nuna.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapRepository extends JpaRepository<Map,Long> {

    List<Map> findByGenre(Genre genre);
}
