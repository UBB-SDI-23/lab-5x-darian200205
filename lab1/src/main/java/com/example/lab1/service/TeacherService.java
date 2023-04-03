package com.example.lab1.service;

import com.example.lab1.domain.Student;
import com.example.lab1.domain.Teacher;
import com.example.lab1.domain.TeacherDTO;
import com.example.lab1.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void delete(Teacher teacher) throws TeacherException {
        Optional<Teacher> deleteTeacher = teacherRepository
                .findByFirstNameAndLastNameAndDepartment(teacher.getFirstName(), teacher.getLastName(), teacher.getDepartment());
        if(deleteTeacher.isPresent()) {
            teacherRepository.delete(deleteTeacher.get());
        } else {
            throw new TeacherException("The teacher does not exist");
        }
    }

    public Teacher findByFirstNameAndLastNameAndDepartment(TeacherDTO teacherDTO) {
        return teacherRepository.findByFirstNameAndLastNameAndDepartment(
                teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getDepartment()
        ).orElse(null);
    }


    public void updateTeacher(Teacher teacher) throws TeacherException {
        Optional<Teacher> searchedTeacher = teacherRepository
                .findByFirstNameAndLastNameAndDepartment(teacher.getFirstName(), teacher.getLastName(), teacher.getDepartment());
        if(searchedTeacher.isPresent()) {
            Teacher editTeacher = searchedTeacher.get();
            editTeacher.setFirstName(teacher.getFirstName());
            editTeacher.setLastName(teacher.getLastName());
            editTeacher.setDepartment(teacher.getDepartment());
            editTeacher.setRating(teacher.getRating());
            teacherRepository.save(editTeacher);
        } else {
            throw new TeacherException("The teacher does not exist");
        }
    }

    public List<Teacher> findTeacherByRatingGreaterThan(Teacher teacher) {
        return teacherRepository.findByRatingGreaterThan(teacher.getRating());
    }
}
