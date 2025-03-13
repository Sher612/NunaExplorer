package com.example.Nuna.service;

import com.example.Nuna.model.Genre;
import com.example.Nuna.model.Map;
import com.example.Nuna.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public List<Map> getAllMaps(){
        return mapRepository.findAll();
    }

    public Optional<Map> getMapById(long id){
        return mapRepository.findById(id);
    }

    public Map createMap(Map map){
        return mapRepository.save(map);
    }

    public void deleteMapById(long id){
        mapRepository.deleteById(id);
    }

    public Map updateMap(Long id, Map updatedMap){
        return mapRepository.findById(id)
                .map(map -> {
                    map.setMapTitle(updatedMap.getMapTitle());
                    map.setMapDescription(updatedMap.getMapDescription());
                    map.setMapImage(updatedMap.getMapImage());
                    map.setGenre(updatedMap.getGenre());
                    map.setStatus(updatedMap.getStatus());
                    return mapRepository.save(map);
                })
                .orElseThrow(() -> new RuntimeException("Map Not Found"));
    }

    //Custom Method to retrieve maps by genre
    public List<Map> getMapsByGenre(Genre genre){
        return mapRepository.findByGenre(genre);
    }
}
