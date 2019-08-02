package com.hasan.movieproject.service.user.base;

import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.model.user.dto.LoginDto;

public interface LoginService {
    Operation login(LoginDto loginDto);
}
