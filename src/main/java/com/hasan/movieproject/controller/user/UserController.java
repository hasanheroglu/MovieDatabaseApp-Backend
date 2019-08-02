package com.hasan.movieproject.controller.user;

import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.user.dto.RegisterDto;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.model.user.dto.PasswordChangeDto;
import com.hasan.movieproject.model.user.dto.UserAddRequestDto;
import com.hasan.movieproject.service.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getUsers(){
        Operation operation = userService.getAll();
        return Operation.getOperationResult(operation);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("#username == authentication.name || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getUserSummary(@PathVariable("username") String username){
        Operation operation = userService.getDetail(username);
        return Operation.getOperationResult(operation);
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserAddRequestDto userAddRequestDto){
        Operation operation = userService.add(userAddRequestDto);
        return Operation.getOperationResult(operation);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto){
        Operation operation = userService.register(registerDto);
        return Operation.getOperationResult(operation);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> remove(@PathVariable("username") String username){
        Operation operation = userService.remove(username);
        return Operation.getOperationResult(operation);
    }

    @PutMapping("/{username}/change_password")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto){
        Operation operation = userService.changePassword(passwordChangeDto);
        return Operation.getOperationResult(operation);
    }

    @PutMapping("/disable")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> disableUser(@RequestParam String username){
        Operation operation = userService.disable(username);
        return Operation.getOperationResult(operation);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody RegisterDto registerDto){
        Operation operation = userService.update(registerDto);
        return Operation.getOperationResult(operation);
    }

    @PostMapping("/{username}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addRole(@PathVariable("username") String username, @RequestParam Long roleId){
        Operation operation = userService.addRole(username, roleId);
        return Operation.getOperationResult(operation);
    }

    @DeleteMapping("/{username}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> removeRole(@PathVariable("username") String username, @RequestParam Long roleId){
        Operation operation = userService.removeRole(username, roleId);
        return Operation.getOperationResult(operation);
    }

    @PostMapping("/{username}/lists")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<?> addListType(@PathVariable("username") String username, @RequestParam String type){
        Operation operation = userService.addListType(username, type);
        return Operation.getOperationResult(operation);
    }



}
