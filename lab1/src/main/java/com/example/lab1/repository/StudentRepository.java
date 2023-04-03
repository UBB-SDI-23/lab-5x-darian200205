package com.example.lab1.repository;

import com.example.lab1.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);


    Long removeByEmailEquals(String email);
    List<Student> findByAgeGreaterThan(Integer age);

    Optional<Student> findStudentById(Long id);

    List<Student> findStudentByFirstNameStartingWith(String firstName);

}
