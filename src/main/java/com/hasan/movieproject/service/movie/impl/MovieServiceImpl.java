package com.hasan.movieproject.service.movie.impl;

import com.hasan.movieproject.model.movie.dao.GenreEntity;
import com.hasan.movieproject.model.user.dao.ListEntity;
import com.hasan.movieproject.model.movie.dto.MovieDto;
import com.hasan.movieproject.service.util.ServiceUtil;
import com.hasan.movieproject.service.util.ImdbUtil;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.movie.repository.MovieRepository;
import com.hasan.movieproject.model.movie.dao.DirectorEntity;
import com.hasan.movieproject.model.movie.dao.MovieEntity;
import com.hasan.movieproject.service.movie.base.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public Operation<?> getAll() {
        Iterable<MovieEntity> movies = movieRepository.findAll();

        if(movies == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }

        return new Operation<>(OperationStatus.MOVIE_FOUND, movies);
    }

    @Override
    public Operation getAllDirectors(Long id) {
        MovieEntity movie = serviceUtil.getMovie(id);

        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND);}

        List<DirectorEntity> directors = movie.getDirectors();
        return new Operation<>(OperationStatus.DIRECTOR_FOUND, directors);
    }

    @Override
    public Operation<?> add(MovieDto movieDto) {

        MovieEntity movie = new MovieEntity(movieDto);
        List<DirectorEntity> directors = resetDirectors(movie);
        List<GenreEntity> genres = resetGenres(movie);

        if(!serviceUtil.isUnique(movie)){ return new Operation<>(OperationStatus.MOVIE_EXISTS); }

        movie = movieRepository.save(movie);

        if(directors != null){ movie.setDirectors(serviceUtil.getUniqueDirectors(directors)); }
        if(genres != null){ movie.setGenres(serviceUtil.getUniqueGenres(genres)); }

        movie = movieRepository.save(movie);

        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_ADD_FAILED); }

        return new Operation<>(OperationStatus.MOVIE_ADD_SUCCESS, movie);
    }

    @Override
    public Operation add(String imdbId){
        MovieEntity newMovie = new MovieEntity(imdbId);

        //if(!serviceUtil.isUnique(newMovie)){ return new Operation<>(OperationStatus.MOVIE_EXISTS); }

        newMovie.setDirectors(serviceUtil.getUniqueDirectors(ImdbUtil.getDirectors()));
        newMovie.setGenres(serviceUtil.getUniqueGenres(ImdbUtil.getGenres()));
        //newMovie = movieRepository.save(newMovie);

        if(newMovie == null){ return new Operation<>(OperationStatus.MOVIE_ADD_FAILED); }

        return new Operation<>(OperationStatus.MOVIE_ADD_SUCCESS, newMovie);
    }

    @Override
    public Operation<?> search(Long id) {
        MovieEntity movie = serviceUtil.getMovie(id);

        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }

        return new Operation<>(OperationStatus.MOVIE_FOUND, movie);
    }

    @Override
    public Operation<?> update(Long id, MovieDto movieDto) {
        MovieEntity toBeUpdated = serviceUtil.getMovie(id);

        MovieEntity movie = new MovieEntity(movieDto);
        List<DirectorEntity> directors = resetDirectors(movie);
        List<GenreEntity> genres = resetGenres(movie);

        if(toBeUpdated == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }
        if(directors != null){ movie.setDirectors(serviceUtil.getUniqueDirectors(directors)); }
        if(genres != null){ movie.setGenres(serviceUtil.getUniqueGenres(genres)); }

        movie = movieRepository.save(movie);
        return new Operation<>(OperationStatus.MOVIE_UPDATE_SUCCESS, movie);
    }

    @Override
    public Operation<?> remove(Long id) {
        MovieEntity toBeRemoved = serviceUtil.getMovie(id);

        if(toBeRemoved == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }

        MovieEntity movie = toBeRemoved;
        clear(movie);
        movieRepository.deleteById(movie.getId());

        return new Operation<>(OperationStatus.MOVIE_REMOVE_SUCCESS, movieRepository.findAll());
    }

    @Override
    public Operation<?> addDirector(Long id, Long directorId) {
        DirectorEntity director = serviceUtil.getDirector(directorId);
        MovieEntity movie = serviceUtil.getMovie(id);

        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }
        if(director == null){ return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND); }

        serviceUtil.addDirectorAndMovie(director, movie);
        return new Operation<>(OperationStatus.MOVIE_DIRECTOR_ADD_SUCCESS, movie);
    }

    @Override
    public Operation<?> removeDirector(Long id, Long directorId) {
        MovieEntity movie = serviceUtil.getMovie(id);

        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }

        for(DirectorEntity director: movie.getDirectors()){
            if(director.getId() == directorId){
                serviceUtil.removeDirectorAndMovie(director, movie);
                return new Operation<>(OperationStatus.DIRECTOR_REMOVE_SUCCESS, movie);
            }
        }

        return new Operation<>(OperationStatus.DIRECTOR_NOT_FOUND);
    }

    @Override
    public Operation search(String name) {
        List<MovieEntity> movies;

        if(name == null || name.isEmpty()){
            movies = movieRepository.findAll();
        } else{
            movies = movieRepository.findAllByNameContaining(name);
        }

        if(movies == null) {return new Operation(OperationStatus.MOVIE_NOT_FOUND);}

        return new Operation<>(OperationStatus.MOVIE_FOUND, movies);
    }

    private List<DirectorEntity> resetDirectors(MovieEntity movie){
        List<DirectorEntity> directors = new ArrayList<>();

        if(movie.getDirectors() != null){
            directors = movie.getDirectors();
            movie.setDirectors(null);
        }

        return directors;
    }

    private List<GenreEntity> resetGenres(MovieEntity movie){
        List<GenreEntity> genres = new ArrayList<>();

        if(movie.getGenres() != null){
            genres = movie.getGenres();
            movie.setGenres(null);
        }

        return genres;
    }

    private void clear(MovieEntity movie){
        for(ListEntity listEntity: movie.getListEntities()){
            listEntity.getMovies().remove(movie);
        }

        for(DirectorEntity director: movie.getDirectors()){
            director.getMovies().remove(movie);
        }
        for(GenreEntity genre: movie.getGenres()){
            genre.getMovies().remove(movie);
        }
    }
}
