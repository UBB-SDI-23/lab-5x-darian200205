package com.example.lab1.repository;

import com.example.lab1.domain.course.Course;
import com.example.lab1.domain.course.CourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);

    Optional<Course> findByNameAndCredits(String name, Integer credits);

}

