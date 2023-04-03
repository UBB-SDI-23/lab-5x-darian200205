package com.example.lab1.domain;
import lombok.Data;


@Data
public class TeacherDTO {

    private String firstName;

    private String lastName;

    private Department department;

    private Integer rating;

}
