package com.example.lab1.domain;

import com.example.lab1.domain.course.Course;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeacherCourse {

    @EmbeddedId
    TeacherCourseKey id;

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

}
