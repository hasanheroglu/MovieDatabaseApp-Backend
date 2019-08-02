package com.hasan.movieproject.model.user.repository;

import com.hasan.movieproject.model.user.dao.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
    ListEntity findByType(String type);
}
