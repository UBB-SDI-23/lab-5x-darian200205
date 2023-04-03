package com.example.lab1.domain.course;

import com.example.lab1.domain.StudentCourse;
import com.example.lab1.domain.TeacherCourse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueNameAndCredits", columnNames = { "name", "credits" }) })
@Data
@EqualsAndHashCode
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer totalHours;

    @OneToMany(mappedBy = "course")
    List<StudentCourse> students;

    @OneToMany(mappedBy = "course")
    List<TeacherCourse> teachers;

    public void addStudent(StudentCourse studentCourse) {
        this.students.add(studentCourse);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", totalHours=" + totalHours +
                '}';
    }
}
