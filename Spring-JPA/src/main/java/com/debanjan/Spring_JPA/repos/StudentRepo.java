package com.debanjan.Spring_JPA.repos;

import com.debanjan.Spring_JPA.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity, Long> {
    public StudentEntity findByName(String name);
}
