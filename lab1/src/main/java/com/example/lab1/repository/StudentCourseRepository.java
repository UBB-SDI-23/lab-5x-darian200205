package com.example.lab1.repository;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
}
