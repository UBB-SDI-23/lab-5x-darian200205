package com.example.lab1.repository;

import com.example.lab1.domain.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
}
