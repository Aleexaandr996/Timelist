package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Student;
import com.example.timelist.error.GroupSizeStudentException;
import com.example.timelist.error.StudentAlreadyHaveGroupException;
import com.example.timelist.error.StudentDuplicateException;
import com.example.timelist.persistence.InMemoryStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    InMemoryStorage storage;

    @InjectMocks
    StudentService studentService;
    @InjectMocks
    Group group;

    @Test
    void addStudent() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setId(UUID.randomUUID().toString());
//        When
        studentService.addStudent(student);
//        Then
        verify(storage).add(student);
    }

    @Test
    void getStudents() {
//        Given
        List <Student> expendStudents = new ArrayList<>();
        when(storage.getStudents()).thenReturn(expendStudents);

        List <Student> students = studentService.getStudents();

        assertThat(students).isSameAs(expendStudents);
    }

    @Test
    void updateStudent() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setId(UUID.randomUUID().toString());

        studentService.updateStudent(student, student.getId());

        verify(storage).updateStudent(student, student.getId());
    }

    @Test
    void deleteStudent() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setId(UUID.randomUUID().toString());

        studentService.deleteStudent(student);

        verify(storage).deleteStudent(student);
    }

    @Test
    void divideStudents() {
    }

    @Test
    void checkStudentDuplicateInGroup() {
    }

    @Test
    void sizeStudentInGroup() {
        Group group = new Group();
        group.setName("MK-21");
        List <String> buffer = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
           group.getStudentId().add("2" + i);
        }
        Assertions.assertThrows(GroupSizeStudentException.class,() -> {
            studentService.sizeStudentInGroup(group);
        });
    }

    @Test
    void checkStDuplicateEx() {
//        Given
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setId(UUID.randomUUID().toString());
        students.add(student);
//           When
        when(storage.getStudents()).thenReturn(students);

        Student duplicate = new Student();
        duplicate.setName("Max");
        duplicate.setAge(15);
        duplicate.setId(student.getId());

//        Then
        Assertions.assertThrows(StudentDuplicateException.class,() -> {
            studentService.addStudent(duplicate);
        });
        verify(storage,times(0)).add(duplicate);
    }

    @Test
    void checkStMoreOneGroup() {
//        Given
        Group groupFirst = new Group();
        group.setName("MK-21");
        groupFirst.setGroupId(UUID.randomUUID().toString());
        Group groupSecond = new Group();
        group.setName("MK-22");
        groupSecond.setGroupId(UUID.randomUUID().toString());
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setId(UUID.randomUUID().toString());
        groupFirst.getStudentId().add(student.getId());

        Assertions.assertThrows(StudentAlreadyHaveGroupException.class,() ->{
            groupSecond.getStudentId().add(student.getId());
        });
        verify(groupSecond.getStudentId(),times(0)).add(student.getId());
    }
}