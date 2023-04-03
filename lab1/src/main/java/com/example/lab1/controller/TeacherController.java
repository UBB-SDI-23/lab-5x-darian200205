package com.example.lab1.controller;

import com.example.lab1.domain.Teacher;
import com.example.lab1.domain.TeacherDTO;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.service.TeacherCourseService;
import com.example.lab1.service.TeacherException;
import com.example.lab1.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {
    private final TeacherService teacherService;

    private final TeacherCourseService teacherCourseService;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherCourseService teacherCourseService) {
        this.teacherService = teacherService;
        this.teacherCourseService = teacherCourseService;
    }

    @GetMapping("teacher/list")
    public String getTeachers() {
        return teacherService.getAll().toString();
    }

    @PostMapping("teacher/add")
    public String addTeacher(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return "added";
    }

    @DeleteMapping("/delete")
    public String deleteTeacher(@RequestBody Teacher teacher) {
        try {
            teacherService.delete(teacher);
        } catch (TeacherException e) {
            return e.getMessage();
        }
        return "deleted";
    }

    @PutMapping("/update")
    public String updateTeacher(@RequestBody Teacher teacher) {
        try {
            teacherService.updateTeacher(teacher);
        } catch (TeacherException e) {
            return "Could not update";
        }
        return "updated";
    }

    @PostMapping("/course/register")
    public String addTeacherToCourse(@RequestBody TeacherDTO teacherDTO, @RequestBody CourseDTO courseDTO) {
        teacherCourseService.addTeacherToCourse(teacherDTO, courseDTO);
        return "registered";
    }
}
