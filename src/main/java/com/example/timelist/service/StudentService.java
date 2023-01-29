package com.example.timelist.service;

import com.example.timelist.persistence.InMemoryStorage;
import com.example.timelist.beans.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final InMemoryStorage storage;

    public Student addStudent ( Student student){
        student.setId(UUID.randomUUID().toString());
        storage.add(student);
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
