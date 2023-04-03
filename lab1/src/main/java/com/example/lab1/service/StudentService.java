package com.example.lab1.service;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.StudentDTO;
import com.example.lab1.domain.course.Course;
import com.example.lab1.domain.course.CourseDTO;
import com.example.lab1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final CourseService courseService;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public List<StudentDTO> getAll() {
        List<Student> students = studentRepository.findAll();
        //return students.stream()
          //      .map(Student::getId)
            //    .collect(Collectors.toList());
        return students.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public Student save(Student student) throws StudentException {
        if(validateStudent(student)) {
            Optional<Student> findByEmail = studentRepository.findByEmail(student.getEmail());
            if(findByEmail.isPresent()) {
                 updateStudent(student);
                 return student;
            }
            return studentRepository.save(student);
        }
        throw new StudentException("The student data is not valid");
    }

    private boolean validateStudent(Student student) {
        if(student.getEmail().isBlank() || student.getEmail() == null) {
            return false;
        }

        if(student.getAge() <= 0 || student.getAge() == null) {
            return false;
        }

        if(student.getLastName().isBlank() || student.getLastName() == null) {
            return false;
        }

        if(student.getFirstName().isBlank() || student.getLastName() == null) {
            return false;
        }

        if(student.getYear() < 1 || student.getYear() > 4) {
            return false;
        }

        return true;
    }

    private StudentDTO convertEntityToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        studentDTO.setYear(student.getYear());
        return studentDTO;
    }

    public void delete(Student student) throws StudentException {
        Optional<Student> deleteStudent = studentRepository.findByEmail(student.getEmail());
        if(deleteStudent.isPresent()) {
            studentRepository.delete(deleteStudent.get());
        } else {
            throw new StudentException("The student does not exist");
        }
    }


    public void updateStudent(Student student) throws StudentException {
        if(validateStudent(student)) {
            Optional<Student> searchedStudent = studentRepository.findByEmail(student.getEmail());
            if(searchedStudent.isPresent()) {
                Student editStudent = searchedStudent.get();
                editStudent.setFirstName(student.getFirstName());
                editStudent.setLastName(student.getLastName());
                editStudent.setAge(student.getAge());
                studentRepository.save(editStudent);
            } else {
                throw new StudentException("The student does not exist");
            }
        } else {
            throw new StudentException("Data not valid");
        }
    }

    public List<Student> findByAgeGreaterThan(Integer age) {
        return studentRepository.findByAgeGreaterThan(age);
    }

    public Student findStudentById(Long id) {
        Optional<Student> student =  studentRepository.findStudentById(id);
        return student.orElse(null);
    }

    public Student findStudentByEmail(StudentDTO studentDTO) {
        if(studentDTO.getEmail() != null) {
            Optional<Student> student = studentRepository.findByEmail(studentDTO.getEmail());
            return student.orElse(null);
        }
        return null;
    }

    public List<Student> filterStudentsByFirstName(StudentDTO studentDTO) {
        return studentRepository.findStudentByFirstNameStartingWith(studentDTO.getFirstName());
    }
}
