package com.example.timelist.controller;

import com.example.timelist.beans.Storage;
import com.example.timelist.beans.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class StudentController {

    @Autowired
    private Storage storage;

    @PostMapping("/students")
    public Student create(@RequestBody @Valid Student student){
        student.setId(UUID.randomUUID().toString());
        storage.checkStDuplicateEx(student);
        storage.getStudents().add(student);
        return student;
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return storage.getStudents();
    }

    @PutMapping("/students")
    public Student update(@RequestBody @Valid Student student){

        storage.updateStudent(student);
        return student;
    }
    @DeleteMapping("/students")
    public void delete(@RequestBody Student student){
        storage.deleteStudent(student);
    }








}
