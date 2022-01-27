package com.stnslv.student;

import com.stnslv.student.exception.BadRequestException;
import com.stnslv.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new BadRequestException(String.format("Email %s is taken already", student.getEmail()));
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("A given student is not found.");
        }
        studentRepository.deleteById(studentId);
    }
}
