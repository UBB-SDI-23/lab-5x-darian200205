package com.example.lab1.domain;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class StudentDTO {

    private String firstName;

    private String lastName;

    private Integer age;

    private Integer year;

    private String email;
}
