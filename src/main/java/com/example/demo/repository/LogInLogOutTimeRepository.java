package com.example.demo.repository;

import com.example.demo.entity.LogInLogOutTime;
import com.example.demo.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogInLogOutTimeRepository extends JpaRepository<LogInLogOutTime, Integer> {
    List<LogInLogOutTime> findAllByUserAndLoginTimeBetween(User user, LocalDateTime start, LocalDateTime end);
}
