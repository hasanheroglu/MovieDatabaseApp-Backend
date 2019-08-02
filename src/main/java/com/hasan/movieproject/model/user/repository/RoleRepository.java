package com.hasan.movieproject.model.user.repository;

import com.hasan.movieproject.model.user.dao.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
