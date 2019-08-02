package com.hasan.movieproject.security;

import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = serviceUtil.getUser(username);

        if(user == null){ return null; }

        return new UserPrincipal(user);
    }
}
