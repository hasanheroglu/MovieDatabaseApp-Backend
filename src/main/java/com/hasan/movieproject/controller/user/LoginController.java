package com.hasan.movieproject.controller.user;

import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.model.user.dto.LoginDto;
import com.hasan.movieproject.service.user.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Operation operation = loginService.login(loginDto);
        return Operation.getOperationResult(operation);
    }
}