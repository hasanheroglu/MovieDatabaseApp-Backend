package com.hasan.movieproject.service.movie.base;

import com.hasan.movieproject.model.movie.dto.DirectorDto;
import com.hasan.movieproject.log.base.Operation;

public interface DirectorService {
    Operation getAll();
    Operation getAllMovies(Long id);
    Operation add(DirectorDto directorDto);
    Operation addMovie(Long id, Long movieId);
    Operation removeMovie(Long id, Long movieId);
    Operation search(Long id);
    Operation search(String name);
    Operation update(Long id, DirectorDto directorDto);
    Operation remove(Long id);
}
