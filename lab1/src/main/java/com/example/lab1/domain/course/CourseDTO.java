package com.example.lab1.domain.course;

import com.example.lab1.domain.StudentCourse;
import com.example.lab1.domain.TeacherCourse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class CourseDTO {

    private String name;

    private Integer credits;

    private Integer totalHours;

    private Double averageStudent;
}
