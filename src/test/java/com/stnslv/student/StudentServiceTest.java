package com.stnslv.student;

import com.stnslv.student.exception.BadRequestException;
import com.stnslv.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService sut;

    @BeforeEach
    void setUp() {
        sut = new StudentService(studentRepository);
    }

    @Test
    void getAllStudents_can_be_invoked_with_its_dependencies() {
        sut.getAllStudents();

        verify(studentRepository).findAll();
    }

    @Test
    void addStudent_should_save_correct_student_to_db() {
        Student student = new Student("Jamila", "email", Gender.FEMALE);

        sut.addStudent(student);

        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(argumentCaptor.capture());
        final Student capturedStudent = argumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void addStudent_should_throw_exception_if_user_exists() {
        Student student = new Student("Jamila", "email", Gender.FEMALE);
        given(studentRepository.existsByEmail(student.getEmail())).willReturn(true);

        assertThatThrownBy(() -> sut.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(String.format("Email %s is taken already", student.getEmail()));

        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudent_should_perform_with_correct_id() {
        given(studentRepository.existsById(123L)).willReturn(true);
        sut.deleteStudent(123L);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).deleteById(argumentCaptor.capture());
        final Long captured = argumentCaptor.getValue();
        assertThat(captured).isEqualTo(123L);
    }

    @Test
    void addStudent_should_throw_exception_if_user_does_not_exist() {
        assertThatThrownBy(() -> sut.deleteStudent(123L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("A given student is not found.");

        verify(studentRepository, never()).deleteById(any());
    }


}