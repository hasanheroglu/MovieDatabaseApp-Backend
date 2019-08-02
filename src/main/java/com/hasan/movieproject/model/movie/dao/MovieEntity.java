package com.hasan.movieproject.model.movie.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.movieproject.model.user.dao.ListEntity;
import com.hasan.movieproject.model.movie.dto.MovieDto;
import com.hasan.movieproject.service.util.ImdbUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

//might add imdb id later!!!

@Entity
@Table(name = "Movies")
public class MovieEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String name;
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "movies_and_directors",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id")
    )
    private List<DirectorEntity> directors;
    @Column
    private Date releaseDate;
    @Column
    private Double imdbRating;
    @Column
    private int duration;
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "movies_and_genres",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private List<GenreEntity> genres;
    @Column
    private String imdbId;
    @ManyToMany(mappedBy = "movies")
    @JsonIgnore
    private List<ListEntity> listEntities;

    public MovieEntity() {
    }

    public MovieEntity(String imdbId){
        ImdbUtil.setProperties(imdbId);
        this.name = ImdbUtil.getTitle();
        this.imdbId = ImdbUtil.getImdbId();
        this.imdbRating = ImdbUtil.getImdbRating();
        this.duration = ImdbUtil.getDuration();
        this.releaseDate = ImdbUtil.getReleaseDate();
    }

    public MovieEntity(MovieDto movieDto){
        this.id = movieDto.getId();
        this.name = movieDto.getName();
        this.releaseDate = movieDto.getReleaseDate();
        this.imdbRating = movieDto.getImdbRating();
        this.duration = movieDto.getDuration();
        this.imdbId = movieDto.getImdbId();
        this.directors = movieDto.getDirectors();
        this.genres = movieDto.getGenres();
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

    public List<DirectorEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorEntity> directors) {
        this.directors = directors;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreEntity> genres) {
        this.genres = genres;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<ListEntity> getListEntities() {
        return listEntities;
    }

    public void setListEntities(List<ListEntity> listEntities) {
        this.listEntities = listEntities;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", directors=" + directors +
                ", releaseDate=" + releaseDate +
                ", imdbRating=" + imdbRating +
                ", duration=" + duration +
                ", genres=" + genres +
                ", imdbId='" + imdbId + '\'' +
                ", listEntities=" + listEntities +
                '}';
    }
}
