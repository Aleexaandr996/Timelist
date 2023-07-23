package com.example.timelist.controller;

import com.example.timelist.beans.Student;
import com.example.timelist.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    @Test
    void create() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setStudentId (UUID.randomUUID().toString());
//        When
        studentController.create(student);
//        Then
        verify(studentService).addStudent(student);
    }

    @Test
    void getStudents() {
//        Given
        List<Student> expendStudents = new ArrayList<>();
        when(studentService.getStudents()).thenReturn(expendStudents);

        List <Student> students = studentController.getStudents();

        assertThat(students).isSameAs(expendStudents);
    }

    @Test
    void update() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setStudentId (UUID.randomUUID().toString());

        studentController.update(student, student.getStudentId ());

        verify(studentService).updateStudent(student, student.getStudentId ());
    }

    @Test
    void delete() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        UUID id = UUID.randomUUID();
        student.setStudentId (id.toString());

        studentController.delete(id);

        verify(studentService).deleteStudent(id);
    }
}