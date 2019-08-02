package com.hasan.movieproject.controller.movie;

import com.hasan.movieproject.model.movie.dto.DirectorDto;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.service.movie.impl.DirectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    DirectorServiceImpl directorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody DirectorDto director){
        Operation operation = operation = directorService.add(director);
        return Operation.getOperationResult(operation);
    }

    @PostMapping("/{id}/movies")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addMovie(@PathVariable("id") Long id, @RequestParam Long movieId){
        Operation operation = directorService.addMovie(id, movieId);
        return Operation.getOperationResult(operation);
    }
/*
    @GetMapping
    public ResponseEntity<?> getDirectors(){
        Operation operation = directorService.getAll();
        return Operation.getOperationResult(operation);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<?> search(@PathVariable("id") Long id){
        Operation operation = directorService.search(id);
        return Operation.getOperationResult(operation);
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam String name){
        Operation operation = directorService.search(name);
        return Operation.getOperationResult(operation);
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<?> getMovies(@PathVariable("id") Long id){
        Operation operation = directorService.getAllMovies(id);
        return Operation.getOperationResult(operation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody DirectorDto director){
        Operation operation = directorService.update(id, director);
        return Operation.getOperationResult(operation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> remove(@PathVariable("id") Long id){
        Operation operation = directorService.remove(id);
        return Operation.getOperationResult(operation);
    }

    @DeleteMapping("/{id}/movies")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> removeMovie(@PathVariable("id") Long id, @RequestParam Long movieId){
        Operation operation = directorService.removeMovie(id, movieId);
        return Operation.getOperationResult(operation);
    }
}
