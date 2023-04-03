package com.example.lab1.service;

import com.example.lab1.domain.Teacher;
import com.example.lab1.domain.TeacherCourse;
import com.example.lab1.domain.TeacherDTO;
import com.example.lab1.domain.course.Course;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.repository.TeacherCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherCourseService {

    private final TeacherCourseRepository teacherCourseRepository;

    private final TeacherService teacherService;

    private final CourseService courseService;

    @Autowired
    public TeacherCourseService(TeacherCourseRepository teacherCourseRepository, TeacherService teacherService, CourseService courseService) {
        this.teacherCourseRepository = teacherCourseRepository;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    public TeacherCourse addTeacherToCourse(TeacherDTO teacherDTO, CourseDTO courseDTO) {
        Teacher teacher = teacherService.findByFirstNameAndLastNameAndDepartment(teacherDTO);
        Course course = courseService.findCourseByNameAndCredits(courseDTO.getName(), courseDTO.getCredits());
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setTeacher(teacher);
        teacherCourse.setCourse(course);
        return teacherCourseRepository.save(teacherCourse);
    }

}
