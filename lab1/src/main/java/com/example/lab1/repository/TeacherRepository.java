package com.example.lab1.repository;

import com.example.lab1.domain.Department;
import com.example.lab1.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByFirstNameAndLastNameAndDepartment(String firstName, String lastName, Department department);

    List<Teacher> findByRatingGreaterThan(Integer rating);
}
