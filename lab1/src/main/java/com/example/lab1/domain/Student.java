package com.example.lab1.domain;

import com.example.lab1.domain.course.Course;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;


import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table
@EqualsAndHashCode
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 16)
    private String firstName;

    @Column(nullable = false, length = 16)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "student")
    List<StudentCourse> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return this.getId().equals(((Student) o).getId());
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", year=" + year +
                ", email='" + email + '\'' +
                '}';
    }

    public void addCourse(StudentCourse studentCourse) {
        this.courses.add(studentCourse);
    }

}
