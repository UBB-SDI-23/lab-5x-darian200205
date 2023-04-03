package com.example.lab1.service;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.StudentCourse;
import com.example.lab1.domain.StudentCourseDTO;
import com.example.lab1.domain.StudentDTO;
import com.example.lab1.domain.course.Course;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository, StudentService studentService, CourseService courseService) {
        this.studentCourseRepository = studentCourseRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public StudentCourse registerStudentToCourse(StudentCourseDTO studentCourseDTO) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail(studentCourseDTO.getStudentEmail());
        Student student = studentService.findStudentByEmail(studentDTO);

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(studentCourseDTO.getCourseName());
        courseDTO.setCredits(studentCourseDTO.getCredits());
        Course course = courseService.findCourseByNameAndCredits(courseDTO.getName(), courseDTO.getCredits());

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setDate(new Date());

        student.addCourse(studentCourse);
        course.addStudent(studentCourse);

        return studentCourseRepository.save(studentCourse);
    }

    public void registerStudentToCourses(StudentDTO studentDTO, List<CourseDTO> courseDTOList) {
        Student student = studentService.findStudentByEmail(studentDTO);
        List<Course> courses = new ArrayList<>();
        for(CourseDTO courseDTO : courseDTOList) {
            Course course = courseService.findCourseByNameAndCredits(courseDTO.getName(), courseDTO.getCredits());
            courses.add(course);
        }

        for(Course course : courses) {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourse(course);
            studentCourse.setStudent(student);
            studentCourseRepository.save(studentCourse);
            student.addCourse(studentCourse);
            course.addStudent(studentCourse);
        }
    }

}
