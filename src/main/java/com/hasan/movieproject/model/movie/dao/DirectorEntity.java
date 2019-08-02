package com.hasan.movieproject.model.movie.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.movieproject.model.movie.dto.DirectorDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Directors")
public class DirectorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    private String surname;
    @Column
    private Date birthDate;
    @ManyToMany(mappedBy = "directors")
    @JsonIgnore
    private List<MovieEntity> movies;
    @Column
    private String birthPlace;

    public DirectorEntity() {
    }

    public DirectorEntity(@NotNull String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public DirectorEntity(DirectorDto directorDto){
        this.id = directorDto.getId();
        this.name = directorDto.getName();
        this.surname = directorDto.getSurname();
        this.birthDate = directorDto.getBirthDate();
        this.birthPlace = directorDto.getBirthPlace();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Override
    public String toString() {
        return "DirectorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }
}
