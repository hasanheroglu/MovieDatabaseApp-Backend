package com.hasan.movieproject.model.movie.repository;

import com.hasan.movieproject.model.movie.dao.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {
    DirectorEntity findByNameAndSurnameAndBirthDate(String name, String surname, Date birthDate);
    List<DirectorEntity> findAllByNameContaining(String name);
}
