package com.hasan.movieproject.service.movie.base;

import com.hasan.movieproject.model.movie.dto.MovieDto;
import com.hasan.movieproject.log.base.Operation;

public interface MovieService {
    Operation getAll();
    Operation getAllDirectors(Long id);
    Operation add(MovieDto movieDto);
    Operation add(String imdbId);
    Operation addDirector(Long id, Long directorId);
    Operation removeDirector(Long id, Long directorId);
    Operation search(Long id);
    Operation search(String name);
    Operation update(Long id, MovieDto movieDto);
    Operation remove(Long id);
}
