package com.stnslv.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

    @Test
    void should_verify_that_student_exists_by_email() {
        final String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);
        sut.save(student);

        final boolean expected = sut.existsByEmail(email);

        assertThat(expected).isTrue();
    }

    @Test
    void should_verify_that_student_does_not_exist_by_email() {
        final String email = "jamila@gmail.com";

        final boolean expected = sut.existsByEmail(email);

        assertThat(expected).isFalse();
    }
}