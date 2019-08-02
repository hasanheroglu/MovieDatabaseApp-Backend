package com.hasan.movieproject.model.user.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.movieproject.model.movie.dao.MovieEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Lists")
public class ListEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "list_entities_and_movies",
            joinColumns = @JoinColumn(name = "list_entity_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
    )
    private List<MovieEntity> movies = new ArrayList<>();
    @Column
    @NotNull
    private String type;
    @ManyToMany(mappedBy = "listEntities")
    @JsonIgnore
    private List<UserEntity> users;

    public ListEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "ListEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
