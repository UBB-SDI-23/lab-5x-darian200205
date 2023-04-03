package com.example.lab1.service;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.StudentCourse;
import com.example.lab1.domain.StudentDTO;
import com.example.lab1.domain.course.Course;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CourseService {
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course save(CourseDTO course) {
        return courseRepository.save(convertDTOToEntity(course));
    }

    public void delete(CourseDTO course) throws CourseException {
        Optional<Course> deleteCourse = courseRepository.findByNameAndCredits(course.getName(), course.getCredits());
        if(deleteCourse.isPresent()) {
            courseRepository.delete(deleteCourse.get());
        } else {
            throw new CourseException("The course does not exist");
        }
    }


    public void update(CourseDTO course) throws CourseException {
        Optional<Course> searchedCourse = courseRepository.findByNameAndCredits(course.getName(), course.getCredits());
        if(searchedCourse.isPresent()) {
            Course editCourse = searchedCourse.get();
            editCourse.setName(course.getName());
            editCourse.setCredits(course.getCredits());
            editCourse.setTotalHours(course.getTotalHours());
            courseRepository.save(editCourse);
        } else {
            throw new CourseException("The course does not exist");
        }
    }

    public Course findCourseById(Long id) {
        Optional<Course> course =  courseRepository.findById(id);
        return course.orElse(null);
    }

    public Course findCourseByNameAndCredits(String name, Integer credits) {
        Optional<Course> course = courseRepository.findByNameAndCredits(name, credits);
        return course.orElse(null);
    }

    public Course convertDTOToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        course.setTotalHours(courseDTO.getTotalHours());
        return course;
    }

    List<Student> getEnrolledStudents(CourseDTO courseDTO) {
        Optional<Course> course = courseRepository.findByNameAndCredits(courseDTO.getName(), courseDTO.getCredits());
        List<StudentCourse> studentCourses = course.get().getStudents();
        return studentCourses.stream()
                .map(StudentCourse::getStudent)
                .collect(Collectors.toList());
    }

    public CourseDTO getAvgStudentsAge(CourseDTO courseDTO) {
        Double avg = (double) 0;
        List<Student> enrolledStudents = getEnrolledStudents(courseDTO);
        for(Student student : enrolledStudents)
            avg += student.getAge();
        if(!enrolledStudents.isEmpty()) {
            courseDTO.setAverageStudent(avg / enrolledStudents.size());
        } else {
            courseDTO.setAverageStudent(Double.valueOf(0));
        }
        return courseDTO;
    }


    private CourseDTO convertEntityToDto(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(course.getName());
        courseDTO.setCredits(course.getCredits());
        courseDTO.setTotalHours(course.getTotalHours());
        return courseDTO;
    }

    public List<CourseDTO> orderCoursesByAvgStudentAge() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for(Course course : courses) {
            courseDTOS.add(
                    getAvgStudentsAge(convertEntityToDto(course))
            );
        }
        return courseDTOS.stream()
                .sorted(Comparator.comparingDouble(CourseDTO::getAverageStudent))
                .collect(Collectors.toList());
    }

    public List<CourseDTO> filterCoursesByTotalHoursAndFilterByTotalHours(CourseDTO courseDTO) {
        List<CourseDTO> orderedCourses = orderCoursesByAvgStudentAge();
        return orderedCourses.stream()
                .filter(course -> course.getTotalHours().compareTo(courseDTO.getTotalHours()) > 0)
                .collect(Collectors.toList());
    }

}
