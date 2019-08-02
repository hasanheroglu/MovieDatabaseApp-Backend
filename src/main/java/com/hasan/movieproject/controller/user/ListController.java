package com.hasan.movieproject.controller.user;

import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.service.user.base.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users/{username}/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @PostMapping("/{type}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> add(@PathVariable("username") String username, @PathVariable("type") String type, @RequestParam Long movieId){
        Operation operation = listService.add(username, type, movieId);
        Operation.logOperation("Add to the "+ type + " list of the user ");
        return Operation.getOperationResult(operation);
    }

    @GetMapping("/{type}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> search(@PathVariable("username") String username, @PathVariable("type") String type){
        Operation operation = listService.search(username, type);
        Operation.logOperation("Search "+ type + " list of the user ");
        return Operation.getOperationResult(operation);
    }


    @DeleteMapping("/{type}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> remove(@PathVariable("username") String username, @PathVariable("type") String type,@RequestParam Long movieId){
        Operation operation = listService.remove(username, type, movieId);
        Operation.logOperation("Remove movie from "+ type + " list of the user with id " + movieId);
        return Operation.getOperationResult(operation);
    }

   @GetMapping
   @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> getListsOf(@PathVariable("username") String username){
        Operation operation = listService.getListsOf(username);
        return Operation.getOperationResult(operation);
   }

   @DeleteMapping
   @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> removeListType(@PathVariable("username") String username, @RequestParam String type){
        Operation operation = listService.removeListType(username, type);
        return Operation.getOperationResult(operation);
   }


}
