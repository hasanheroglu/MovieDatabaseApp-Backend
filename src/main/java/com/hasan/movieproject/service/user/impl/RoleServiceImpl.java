package com.hasan.movieproject.service.user.impl;

import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.user.dao.RoleEntity;
import com.hasan.movieproject.model.user.repository.RoleRepository;
import com.hasan.movieproject.service.user.base.RoleService;
import com.hasan.movieproject.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public Operation getAll() {
        Iterable<RoleEntity> roles = roleRepository.findAll();

        if(roles == null){ return new Operation(OperationStatus.ROLE_NOT_FOUND);}

        return new Operation(OperationStatus.ROLE_FOUND, roles);
    }

    @Override
    public Operation search(Long id) {
        RoleEntity role = serviceUtil.getRole(id);

        if(role == null){ return new Operation(OperationStatus.ROLE_NOT_FOUND);}

        return new Operation(OperationStatus.ROLE_FOUND, role);
    }
}
