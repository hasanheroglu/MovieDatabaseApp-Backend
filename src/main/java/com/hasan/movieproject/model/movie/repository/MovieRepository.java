package com.hasan.movieproject.model.movie.repository;

import com.hasan.movieproject.model.movie.dao.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    MovieEntity findByNameAndReleaseDate(String name, Date releaseDate);
    List<MovieEntity>  findAllByNameContaining(String name);

}
