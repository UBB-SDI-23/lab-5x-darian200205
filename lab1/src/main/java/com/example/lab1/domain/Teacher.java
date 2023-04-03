package com.example.lab1.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Department department;

    @Column(nullable = false)
    private Integer rating;

    @OneToMany(mappedBy = "teacher")
    private Set<TeacherCourse> courses;
}
