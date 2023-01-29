package com.example.timelist.controller;

import com.example.timelist.persistence.InMemoryStorage;
import com.example.timelist.beans.Student;
import com.example.timelist.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private InMemoryStorage storage;
    private StudentService studentService;

    @PostMapping("/students")
    public Student create(@RequestBody @Valid Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PutMapping("/students")
    public Student update(@RequestBody @Valid Student student){
        return studentService.updateStudent(student);
    }
    @DeleteMapping("/students")
    public void delete(@RequestBody Student student){
        studentService.deleteStudent(student);
    }

}
