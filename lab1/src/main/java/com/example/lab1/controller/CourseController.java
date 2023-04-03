package com.example.lab1.controller;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.service.CourseException;
import com.example.lab1.service.CourseService;
import com.example.lab1.service.StudentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("course/list")
    public String getCourses() {
        return courseService.getAll().toString();
    }


    @PostMapping("course/add")
    public String add(@RequestBody CourseDTO courseDTO) {
        courseService.save(courseDTO);
        return "added";
    }

    @DeleteMapping("course/delete")
    public String delete(@RequestBody CourseDTO courseDTO) {
        try {
            courseService.delete(courseDTO);
        } catch (CourseException e) {
            return e.getMessage();
        }
        return "deleted";
    }

    @PutMapping("course/update")
    public String update(@RequestBody CourseDTO courseDTO) {
        try {
            courseService.update(courseDTO);
        } catch (CourseException e) {
            throw new RuntimeException(e);
        }
        return "updated";
    }

    @GetMapping("course/report/student/age/filter/totalHours")
    public List<CourseDTO> getCoursesByStudentAvgAgeAndFilterByTotalHours(@RequestBody CourseDTO courseDTO) {
        return courseService.filterCoursesByTotalHoursAndFilterByTotalHours(courseDTO);
    }

    @GetMapping("course/report/student/age")
    public String getCoursesByStudentAvgAge() {
        return courseService.orderCoursesByAvgStudentAge().toString();
    }


}
