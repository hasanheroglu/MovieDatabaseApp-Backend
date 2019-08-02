package com.hasan.movieproject.model.movie.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.movieproject.model.movie.dto.GenreDto;

import javax.persistence.*;
import java.util.List;

@Entity
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private List<MovieEntity> movies;

    public GenreEntity() {
    }

    public GenreEntity(String name) {
        this.name = name;
    }

    public GenreEntity(GenreDto genreDto){
        this.id = genreDto.getId();
        this.name = genreDto.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "GenreEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
