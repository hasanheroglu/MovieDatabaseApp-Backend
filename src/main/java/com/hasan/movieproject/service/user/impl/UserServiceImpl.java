package com.hasan.movieproject.service.user.impl;

import com.hasan.movieproject.model.user.dao.ListEntity;
import com.hasan.movieproject.model.user.dao.RoleEntity;
import com.hasan.movieproject.model.user.dto.RegisterDto;
import com.hasan.movieproject.model.user.dto.UserAddRequestDto;
import com.hasan.movieproject.model.user.repository.RoleRepository;
import com.hasan.movieproject.service.util.ServiceUtil;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.model.user.repository.UserRepository;
import com.hasan.movieproject.model.user.dto.PasswordChangeDto;
import com.hasan.movieproject.service.user.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public Operation<?> getAll() {
        Iterable<UserEntity> users = userRepository.findAll();

        if(users != null){
            return new Operation<>(OperationStatus.USER_FOUND, users);
        } else{
            return new Operation<>(OperationStatus.USER_NOT_FOUND);
        }
    }

    public Operation<?> register(RegisterDto registerDto){
        UserEntity newUser = serviceUtil.getUser(registerDto.getUsername());

        if(newUser != null){ return new Operation<>(OperationStatus.USER_EXISTS); }

        UserEntity user = new UserEntity(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        userRepository.save(user);

        return new Operation<>(OperationStatus.USER_ADD_SUCCESS, user);
    }

    public Operation<?> changePassword(PasswordChangeDto passwordChangeDto){
        UserEntity user = serviceUtil.getUser(passwordChangeDto.getUsername());

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }
        if(!user.getPassword().equals(passwordChangeDto.getOldPassword())){ return new Operation<>(OperationStatus.PASSWORD_WRONG); }
        if(!passwordChangeDto.getPassword().equals(passwordChangeDto.getMatchingPassword())) {
            return new Operation<>(OperationStatus.PASSWORD_CONFIRMATION_FAILED);
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
        userRepository.save(user);
        return new Operation<>(OperationStatus.PASSWORD_CHANGE_SUCCESS, user);
    }

    public Operation<UserEntity> getDetail(String username){
        UserEntity user = serviceUtil.getUser(username);

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        return new Operation<>(OperationStatus.USER_FOUND, user);
    }

    @Override
    public Operation add(UserAddRequestDto userAddRequestDto) {
        UserEntity newUser = serviceUtil.getUser(userAddRequestDto.getUsername());

        if(newUser != null){ return new Operation<>(OperationStatus.USER_EXISTS); }

        UserEntity user = new UserEntity(userAddRequestDto);
        user.setPassword(passwordEncoder.encode(userAddRequestDto.getPassword()));

        List<RoleEntity> roles = user.getRoles();
        user.getRoles().clear();

        user = userRepository.save(user);
        user.setRoles(roles);
        user = userRepository.save(user);

        return new Operation<>(OperationStatus.USER_ADD_SUCCESS, user);
    }

    public Operation<?> disable(String username){
        UserEntity user = serviceUtil.getUser(username);

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        user.setStatus("disabled");
        userRepository.save(user);

        return new Operation<>(OperationStatus.USER_DISABLE_SUCCESS, user);
    }

    public Operation<?> getUser(String username){
        UserEntity user = serviceUtil.getUser(username);

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        return new Operation<>(OperationStatus.USER_FOUND, user);
    }

    public Operation<?> update(RegisterDto registerDto){
        UserEntity user = new UserEntity(registerDto);
        UserEntity toBeUpdated = serviceUtil.getUser(user.getUsername());

        if(toBeUpdated == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }
        if(!passwordEncoder.matches(user.getPassword(), toBeUpdated.getPassword())){ return new Operation<>(OperationStatus.PASSWORD_WRONG); }
        toBeUpdated.setEmail(user.getEmail());
        toBeUpdated.setName(user.getName());
        toBeUpdated.setSurname(user.getSurname());
        toBeUpdated = userRepository.save(toBeUpdated);
        return new Operation<>(OperationStatus.USER_UPDATE_SUCCESS, toBeUpdated);
    }

    @Override
    public Operation remove(String username) {
        UserEntity toBeRemoved = serviceUtil.getUser(username);

        if(toBeRemoved == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND);}

        userRepository.delete(toBeRemoved);
        return new Operation<>(OperationStatus.USER_REMOVE_SUCCESS, userRepository.findAll());
    }

    @Override
    public Operation addRole(String username, Long roleId) {
        UserEntity user = serviceUtil.getUser(username);
        RoleEntity role = serviceUtil.getRole(roleId);

        if(user == null){return new Operation<>(OperationStatus.USER_NOT_FOUND);}
        if(role == null){return new Operation<>(OperationStatus.ROLE_NOT_FOUND);}

        for(RoleEntity r: user.getRoles()){
            if(r.getName().equals(role.getName())){
                return new Operation<>(OperationStatus.ROLE_EXISTS);
            }
        }

        user.getRoles().add(role);
        userRepository.save(user);
        return new Operation<>(OperationStatus.ROLE_ADD_SUCCESS, user);
    }

    @Override
    public Operation removeRole(String username, Long roleId) {
        UserEntity user = serviceUtil.getUser(username);
        RoleEntity role = serviceUtil.getRole(roleId);

        if(user == null){return new Operation<>(OperationStatus.USER_NOT_FOUND);}
        if(role == null){return new Operation<>(OperationStatus.ROLE_NOT_FOUND);}

        if(user.getRoles().remove(role)){
            userRepository.save(user);
            return new Operation<>(OperationStatus.ROLE_REMOVE_SUCCESS, user);
        } else{
            return new Operation<>(OperationStatus.ROLE_REMOVE_FAILED);
        }
    }

    @Override
    public Operation addListType(String username, String type) {
        UserEntity user = serviceUtil.getUser(username);

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND);}

        ListEntity listEntity = new ListEntity();
        listEntity.setType(type);

        for(ListEntity list: user.getListEntities()){
            if(list.getType().equals(type)){
                return new Operation<>(OperationStatus.LIST_TYPE_EXISTS);
            }
        }

        user.getListEntities().add(listEntity);
        userRepository.save(user);

        return new Operation<>(OperationStatus.LIST_TYPE_ADD_SUCCESS, user.getListEntities());
    }

    private void clear(UserEntity user){
        user.getRoles().clear();
        user.getListEntities().clear();
    }
}
