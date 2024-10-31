package com.example.demo.repository;

import com.example.demo.entity.LogInLogOutTime;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogInLogOutTimeRepository extends JpaRepository<LogInLogOutTime, Integer> {
}
