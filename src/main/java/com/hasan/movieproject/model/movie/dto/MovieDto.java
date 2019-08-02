package com.hasan.movieproject.model.movie.dto;

import com.hasan.movieproject.model.movie.dao.DirectorEntity;
import com.hasan.movieproject.model.movie.dao.GenreEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDto {
    private Long id;
    private String name;
    private Date releaseDate;
    private int duration;
    private Double imdbRating;
    private String imdbId;
    private List<DirectorDto> directors;
    private List<GenreDto> genres;

    public MovieDto() {
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<DirectorEntity> getDirectors() {
        List<DirectorEntity> directors = new ArrayList<>();

        if(this.directors == null) {return null;}

        for(DirectorDto directorDto: this.directors){
            DirectorEntity director = new DirectorEntity(directorDto);
            directors.add(director);
        }

        return directors;
    }

    public void setDirectors(List<DirectorDto> directors) {
        this.directors = directors;
    }

    public List<GenreEntity> getGenres() {
        List<GenreEntity> genres = new ArrayList<>();

        for(GenreDto genreDto: this.genres){
            GenreEntity genre = new GenreEntity(genreDto);
            genres.add(genre);
        }

        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }
}
