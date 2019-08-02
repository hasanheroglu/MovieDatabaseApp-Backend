package com.hasan.movieproject.service.util;

import com.hasan.movieproject.model.movie.dao.DirectorEntity;
import com.hasan.movieproject.model.movie.dao.GenreEntity;
import com.hasan.movieproject.model.movie.dao.MovieEntity;
import com.hasan.movieproject.model.user.dao.RoleEntity;
import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.model.movie.repository.DirectorRepository;
import com.hasan.movieproject.model.movie.repository.GenreRepository;
import com.hasan.movieproject.model.movie.repository.MovieRepository;
import com.hasan.movieproject.model.user.repository.RoleRepository;
import com.hasan.movieproject.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceUtil {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public boolean isUnique(MovieEntity movie){
        return movieRepository.findByNameAndReleaseDate(movie.getName(), movie.getReleaseDate()) == null;
    }

    public boolean isUnique(DirectorEntity director){
        return directorRepository.findByNameAndSurnameAndBirthDate(director.getName(), director.getSurname(), director.getBirthDate()) == null;
    }


    public MovieEntity getUniqueMovie(MovieEntity movie){
        MovieEntity movieEntity = movieRepository.findByNameAndReleaseDate(movie.getName(), movie.getReleaseDate());
        if(movieEntity != null){
            return movieEntity;
        } else{
            return movie;
        }
    }

    public DirectorEntity getUniqueDirector(DirectorEntity director){
        DirectorEntity directorEntity = directorRepository.findByNameAndSurnameAndBirthDate(director.getName(),
                director.getSurname(), director.getBirthDate());

        if(directorEntity != null){ return directorEntity; }
        return director;
    }

    public GenreEntity getUniqueGenre(GenreEntity genre){
        GenreEntity genreEntity = genreRepository.findByName(genre.getName());
        if(genreEntity != null){
            return genreEntity;
        } else{
            return genre;
        }
    }

    public List<MovieEntity> getUniqueMovies(List<MovieEntity> movies){
        List<MovieEntity> uniqueMovies = new ArrayList<>();

        for(MovieEntity movie: movies){
            uniqueMovies.add(getUniqueMovie(movie));
        }

        return uniqueMovies;
    }

    public List<DirectorEntity> getUniqueDirectors(List<DirectorEntity> directors){
        List<DirectorEntity> uniqueDirectors = new ArrayList<>();

        for(DirectorEntity director: directors){
            uniqueDirectors.add(getUniqueDirector(director));
        }

        return uniqueDirectors;
    }

    public List<GenreEntity> getUniqueGenres(List<GenreEntity> genres){
        List<GenreEntity> uniqueGenres = new ArrayList<>();

        for(GenreEntity genre: genres){
            uniqueGenres.add(getUniqueGenre(genre));
        }

        return uniqueGenres;
    }

    public DirectorEntity getDirector(Long id){
        Optional<DirectorEntity> directorEntity = directorRepository.findById(id);
        return directorEntity.orElse(null);
    }

    public MovieEntity getMovie(Long id){
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);
        return movieEntity.orElse(null);
    }

    public UserEntity getUser(String username){
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.orElse(null);
    }

    public RoleEntity getRole(Long id){
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        return roleEntity.orElse(null);
    }

    public void addDirectorAndMovie(DirectorEntity director, MovieEntity movie){
        director.getMovies().add(movie);
        movie.getDirectors().add(director);

        directorRepository.save(director);
        movieRepository.save(movie);
    }

    public void removeDirectorAndMovie(DirectorEntity director, MovieEntity movie){
        DirectorEntity toBeRemoved = director;

        director.getMovies().remove(movie);
        movie.getDirectors().remove(toBeRemoved);

        directorRepository.save(director);
        movieRepository.save(movie);
    }
}
