package com.example.timelist.controller;

import com.example.timelist.persistence.InMemoryStorage;
import com.example.timelist.beans.Student;
import com.example.timelist.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/students")
    public void create(@RequestBody @Valid Student student){
         studentService.addStudent(student);
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PutMapping("/students/{id}")
    public void update(@RequestBody @Valid Student student, @PathVariable("id") String studentId){
        studentService.updateStudent(student, studentId);
    }
    @DeleteMapping("/students")
    public void delete(@RequestBody Student student){
        studentService.deleteStudent(student);
    }
}
