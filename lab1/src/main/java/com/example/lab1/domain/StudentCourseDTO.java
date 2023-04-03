package com.example.lab1.domain;

import com.example.lab1.domain.course.Course;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Date;

@Data
public class StudentCourseDTO {
    String studentEmail;

    String courseName;
    Integer credits;

    Integer grade;

    String date;
}
