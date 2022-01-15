package com.stnslv.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @GetMapping
    public List<Student> getAllStudents() {
        return Arrays.asList(new Student(1L, "Stanislav", "stas@gmail.com", Gender.MALE),
                new Student(2L, "Regina", "regina@gmail.com", Gender.FEMALE));
    }
}
