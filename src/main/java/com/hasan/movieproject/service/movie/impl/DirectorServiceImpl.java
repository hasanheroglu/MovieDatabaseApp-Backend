package com.hasan.movieproject.service.movie.impl;

import com.hasan.movieproject.model.movie.dto.DirectorDto;
import com.hasan.movieproject.service.util.ServiceUtil;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.movie.dao.MovieEntity;
import com.hasan.movieproject.model.movie.repository.DirectorRepository;
import com.hasan.movieproject.model.movie.dao.DirectorEntity;
import com.hasan.movieproject.service.movie.base.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public Operation<?> getAll() {
        Iterable<DirectorEntity> directors = directorRepository.findAll();

        if(directors == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        return new Operation<>(OperationStatus.DIRECTOR_FOUND, directors);
    }

    @Override
    public Operation getAllMovies(Long id) {
        DirectorEntity director = serviceUtil.getDirector(id);

        if(director == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        List<MovieEntity> movies = director.getMovies();
        return new Operation<>(OperationStatus.MOVIE_FOUND, movies);
    }

    @Override
    public Operation<?> add(DirectorDto directorDto) {
        DirectorEntity director = new DirectorEntity(directorDto);

        if(!serviceUtil.isUnique(director)){ return new Operation<>(OperationStatus.DIRECTOR_EXISTS); }

        director = directorRepository.save(director);

        if(director == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        return new Operation<>(OperationStatus.DIRECTOR_ADD_SUCCESS, director);
    }

    @Override
    public Operation addMovie(Long id, Long movieId) {
        DirectorEntity director = serviceUtil.getDirector(id);
        MovieEntity movie = serviceUtil.getMovie(movieId);

        if(director == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }
        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND);}

        serviceUtil.addDirectorAndMovie(director, movie);
        return new Operation<>(OperationStatus.DIRECTOR_MOVIE_ADD_SUCCESS, movie);
    }

    @Override
    public Operation removeMovie(Long id, Long movieId) {
        DirectorEntity director = serviceUtil.getDirector(id);

        if(director == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        for(MovieEntity movie: director.getMovies()){
            if(movie.getId() == movieId){
                serviceUtil.removeDirectorAndMovie(director, movie);
                return new Operation<>(OperationStatus.MOVIE_REMOVE_SUCCESS, movie);
            }
        }

        return new Operation<>(OperationStatus.MOVIE_NOT_FOUND);
    }

    @Override
    public Operation<?> search(Long id) {
        DirectorEntity director = serviceUtil.getDirector(id);

        if(director == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        return new Operation<>(OperationStatus.DIRECTOR_FOUND, director);
    }

    @Override
    public Operation update(Long id, DirectorDto directorDto) {
        DirectorEntity director = new DirectorEntity(directorDto);
        DirectorEntity toBeUpdated = serviceUtil.getDirector(id);

        if(toBeUpdated == null ){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        directorRepository.save(director);
        return new Operation<>(OperationStatus.DIRECTOR_UPDATE_SUCCESS, director);
    }

    @Override
    public Operation remove(Long id) {
        DirectorEntity toBeRemoved = serviceUtil.getDirector(id);
        DirectorEntity director = toBeRemoved;

        if(toBeRemoved == null){
            return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND);
        }

        clear(director);
        directorRepository.deleteById(toBeRemoved.getId());
        return new Operation<>(OperationStatus.DIRECTOR_REMOVE_SUCCESS, directorRepository.findAll());
    }

    @Override
    public Operation search(String name) {
        List<DirectorEntity> directors;

        if(name == null || name.isEmpty()) {
            directors = directorRepository.findAll();
        } else{
            directors = directorRepository.findAllByNameContaining(name);
        }

        if(directors == null){return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND);}

        return new Operation<>(OperationStatus.DIRECTOR_FOUND, directors);
    }

    private void clear(DirectorEntity director){
        for(MovieEntity movie: director.getMovies()) {
            movie.getDirectors().remove(director);
        }
    }

}
