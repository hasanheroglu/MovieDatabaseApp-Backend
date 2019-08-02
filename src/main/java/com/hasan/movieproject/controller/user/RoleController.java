package com.hasan.movieproject.controller.user;

import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.service.user.base.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        Operation operation = roleService.getAll();
        return Operation.getOperationResult(operation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> search(@PathVariable("id") Long id){
        Operation operation = roleService.search(id);
        return Operation.getOperationResult(operation);
    }
}
