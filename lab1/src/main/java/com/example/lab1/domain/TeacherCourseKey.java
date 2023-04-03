package com.example.lab1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class TeacherCourseKey implements Serializable {

    @Column(name = "teacher_id")
    Long teacherId;

    @Column(name = "course_id")
    Long courseId;
}
