package com.example.lab1.controller;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.StudentCourseDTO;
import com.example.lab1.domain.StudentDTO;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.service.StudentCourseService;
import com.example.lab1.service.StudentException;
import com.example.lab1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
public class StudentController {

    private StudentService studentService;

    private final StudentCourseService studentCourseService;

    @Autowired
    public StudentController(StudentService studentService, StudentCourseService studentCourseService) {
        this.studentService = studentService;
        this.studentCourseService = studentCourseService;
    }

    @GetMapping("student/list")
    public ResponseEntity<List<StudentDTO>> getStudents() {
        List<StudentDTO> students = studentService.getAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("student/{id}")
    public String getStudent(@PathVariable("id") String id) {
        return studentService.findStudentById(Long.valueOf(id)).toString();
    }

    @PostMapping("student/add")
    public String addStudent(@RequestBody Student student) {
        try {
            studentService.save(student);
        } catch (StudentException exception) {
            return exception.getMessage();
        }
        return "added";
    }

    @DeleteMapping("student/delete")
    public String deleteStudent(@RequestBody Student student) {
        try {
            studentService.delete(student);
        } catch (StudentException e) {
            return e.getMessage();
        }
        return "deleted";
    }

    @PutMapping("student/update")
    public String updateStudent(@RequestBody Student student) {
        try {
            studentService.updateStudent(student);
        } catch (StudentException e) {
            return "Could not update";
        }
        return "updated";
    }

    @GetMapping("student/filter/age>{value}")
    public List<Student> filterStudentByAgeGreaterThan(@PathVariable("value") String age) {
        return studentService.findByAgeGreaterThan(Integer.parseInt(age));
    }

    @GetMapping("student/filter/{name}")
    public List<Student> filterStudentsByFirstName(@PathVariable("name")String name) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(name);
        return studentService.filterStudentsByFirstName(studentDTO);
    }

    @PostMapping("/student/course/register")
    public String registerStudent(@RequestBody StudentCourseDTO studentCourseDTO) {
        studentCourseService.registerStudentToCourse(studentCourseDTO);
        return "registered";
    }

    @PostMapping("/student/{email}/add/courses")
    public String registerStudentToCourses(@PathVariable("email") String email, @RequestBody List<CourseDTO> courseDTOList) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail(email + "@gmail.com");
        studentCourseService.registerStudentToCourses(studentDTO, courseDTOList);
        return "added";
    }

}
