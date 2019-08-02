package com.hasan.movieproject.controller.movie;

import com.hasan.movieproject.model.movie.dto.MovieDto;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.service.movie.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieServiceImpl movieService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody MovieDto movie){
        Operation operation = movieService.add(movie);
        Operation.logOperation("Add movie");
        return Operation.getOperationResult(operation);
    }

    @PostMapping("/{imdbId}")
    public ResponseEntity<?> add(@PathVariable("imdbId") String imdbId){
        Operation operation = movieService.add(imdbId);
        Operation.logOperation("Add movie with IMDB ID " + imdbId);
        return Operation.getOperationResult(operation);
    }

    @PostMapping("/{id}/directors")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addDirector(@PathVariable("id") Long id, @RequestParam Long directorId){
        Operation operation = movieService.addDirector(id, directorId);
        Operation.logOperation("Add director to the movie with id " + id);
        return Operation.getOperationResult(operation);
    }
/*
    @GetMapping
    public ResponseEntity<?> getMovies(){
        Operation operation = movieService.getAll();
        Operation.logOperation("Get movies");
        return Operation.getOperationResult(operation);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<?> search(@PathVariable("id") Long id){
        Operation operation = movieService.search(id);
        Operation.logOperation("Search movies with id " + id);
        return Operation.getOperationResult(operation);
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam String name){
        Operation operation = movieService.search(name);
        return Operation.getOperationResult(operation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MovieDto movie){
        Operation operation = movieService.update(id, movie);
        Operation.logOperation("Update the movie with id " + id);
        return Operation.getOperationResult(operation);
    }


    @GetMapping("/{id}/directors")
    public ResponseEntity<?> getDirectors(@PathVariable("id") Long id){
        Operation operation = movieService.getAllDirectors(id);
        Operation.logOperation("Get directors of the movie with id " + id);
        return Operation.getOperationResult(operation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> remove(@PathVariable("id") Long id){
        Operation operation = movieService.remove(id);
        Operation.logOperation("Remove the movie with id " + id);
        return Operation.getOperationResult(operation);
    }

    @DeleteMapping("/{id}/directors")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> removeDirector(@PathVariable("id") Long id, @RequestParam Long directorId){
        Operation operation = movieService.removeDirector(id, directorId);
        Operation.logOperation("Remove director with id " + directorId + " to the movie with id " + id);
        return Operation.getOperationResult(operation);
    }
}
