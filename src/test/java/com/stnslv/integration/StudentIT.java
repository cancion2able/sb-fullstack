package com.stnslv.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stnslv.student.Gender;
import com.stnslv.student.Student;
import com.stnslv.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
class StudentIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void canAddNewStudent() throws Exception {
        Student student = new Student("Jamila", "jamila12345@gmail.com", Gender.FEMALE);

        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
        final List<Student> students = studentRepository.findAll();
        assertThat(students)
                .usingElementComparatorIgnoringFields("id")
                .contains(student);
    }
}
