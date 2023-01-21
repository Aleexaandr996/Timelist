package com.example.timelist.service;

import com.example.timelist.beans.Storage;
import com.example.timelist.beans.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final Storage storage;

    public Student addStudent ( Student student){
        student.setId(UUID.randomUUID().toString());
        storage.checkStDuplicateEx(student);
        storage.getStudents().add(student);
        return student;
    }

    public List<Student> getStudents(){
        return storage.getStudents();
    }

    public Student updateStudent (Student student){
        storage.updateStudent(student);
        return student;
    }

    public void deleteStudent (Student student){
        storage.deleteStudent(student);
    }
}
