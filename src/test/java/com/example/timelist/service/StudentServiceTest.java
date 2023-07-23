package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Student;
import com.example.timelist.error.StudentSizeInGroupException;
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

    @Test
    void ifAddNewStudentThenStudentIsCreate() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setStudentId (UUID.randomUUID().toString());
//        When
        studentService.addStudent(student);
//        Then
        verify(storage).add(student);
        assertThat(student.getStudentId ()).isNotNull();
    }

    @Test
    void ifGetStudentsThenStorageInvoked() {
//        Given
        List <Student> expendStudents = new ArrayList<>();
        when(storage.getStudents()).thenReturn(expendStudents);

        List <Student> students = studentService.getStudents();

        assertThat(students).isSameAs(expendStudents);
    }

    @Test
    void IfUpdateStudentThenNewStudentSaveOnPlaceOldStudent() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        student.setStudentId (UUID.randomUUID().toString());

        studentService.updateStudent(student, student.getStudentId ());

        verify(storage).updateStudent(student, student.getStudentId ());
        assertThat(student.getStudentId ()).isNotNull();
    }

    @Test
    void ifDeleteStudentThenGivenStudentRemove() {
        Student student = new Student();
        student.setName("Max");
        student.setAge(15);
        UUID id = UUID.randomUUID();
        student.setStudentId (id.toString());

        studentService.deleteStudent(id);

        verify(storage).deleteStudent(id);
    }


    @Test
    void sizeStudentInGroup() {
        Group group = new Group();
        group.setName("MK-21");
        for (int i = 0; i < 21; i++) {
            group.getStudentIds().add(i,"1");
        }
        Assertions.assertThrows(StudentSizeInGroupException.class,() -> {
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
        student.setStudentId (UUID.randomUUID().toString());
        students.add(student);
//           When
        when(storage.getStudents()).thenReturn(students);

        Student duplicate = new Student();
        duplicate.setName("Max");
        duplicate.setAge(15);
        duplicate.setStudentId (student.getStudentId ());

//        Then
        Assertions.assertThrows(StudentDuplicateException.class,() -> {
            studentService.addStudent(duplicate);
        });
        verify(storage,times(0)).add(duplicate);
    }
}