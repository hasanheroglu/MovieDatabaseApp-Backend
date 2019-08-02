package com.hasan.movieproject.service.user.impl;

import com.hasan.movieproject.service.util.ServiceUtil;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.model.user.dto.LoginDto;
import com.hasan.movieproject.service.user.base.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ServiceUtil serviceUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Operation login(@Valid LoginDto loginDto) {
        UserEntity user = serviceUtil.getUser(loginDto.getUsername());

        if (user == null) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) { return new Operation<>(OperationStatus.PASSWORD_WRONG); }

        return new Operation<>(OperationStatus.LOGIN_SUCCESS, user);
    }
}
