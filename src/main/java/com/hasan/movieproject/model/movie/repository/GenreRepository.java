package com.hasan.movieproject.model.movie.repository;

import com.hasan.movieproject.model.movie.dao.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity findByName(String name);
}
