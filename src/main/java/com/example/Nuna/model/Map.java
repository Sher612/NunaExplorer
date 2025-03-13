package com.example.Nuna.model;


import jakarta.persistence.*;

@Entity
@Table(name = "maps")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "explorer_id", nullable = false) //foreign key in maps table
    private Explorer explorer;

    private String creatorName;  //name of the user who created the map
    private String mapTitle;     //Title of the map
    private String mapDescription; //Description of the map
    private String mapImage;      //URL for the map preview image
    private double price;        // price (free or premium maps)

    @Enumerated(EnumType.STRING)
    private Genre genre; // uses predefined categories instead of free text

    @Enumerated(EnumType.STRING)
    private MapStatus status;


    //Empty constructor
    public Map() {}

    //manually inserting getter and setter methods for attributes
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public String getMapDescription() {
        return mapDescription;
    }

    public void setMapDescription(String mapDescription) {
        this.mapDescription = mapDescription;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public MapStatus getStatus() {
        return status;
    }

    public void setStatus(MapStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", mapTitle='" + mapTitle + '\'' +
                ", price=" + price +
                ", genre=" + genre +
                ", status=" + status +
                '}';
    }
}
