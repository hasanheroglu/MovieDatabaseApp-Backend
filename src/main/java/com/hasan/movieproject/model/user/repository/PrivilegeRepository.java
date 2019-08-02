package com.hasan.movieproject.model.user.repository;

import com.hasan.movieproject.model.user.dao.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByName(String name);
}
