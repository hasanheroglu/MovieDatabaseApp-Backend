package com.hasan.movieproject.service.user.base;

import com.hasan.movieproject.log.base.Operation;

public interface RoleService {
    Operation getAll();
    Operation search(Long id);
}
