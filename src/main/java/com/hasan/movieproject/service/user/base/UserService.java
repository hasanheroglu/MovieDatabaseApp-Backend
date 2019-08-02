package com.hasan.movieproject.service.user.base;

import com.hasan.movieproject.model.user.dto.RegisterDto;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.model.user.dto.PasswordChangeDto;
import com.hasan.movieproject.model.user.dto.UserAddRequestDto;

public interface UserService {
    Operation getAll();
    Operation getUser(String username);
    Operation getDetail(String username);
    Operation add(UserAddRequestDto userAddRequestDto);
    Operation register(RegisterDto registerDto);
    Operation update(RegisterDto registerDto);
    Operation changePassword(PasswordChangeDto passwordChangeDto);
    Operation disable(String username);
    Operation remove(String username);
    Operation addRole(String username, Long roleId);
    Operation removeRole(String username, Long roleId);
    Operation addListType(String username, String type);

}
