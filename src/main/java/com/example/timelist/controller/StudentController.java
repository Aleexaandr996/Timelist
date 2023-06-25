package com.example.timelist.controller;

import com.example.timelist.beans.Student;
import com.example.timelist.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/students")
    public CreatedResponseStudent create(@RequestBody @Valid Student student){
       UUID id = studentService.addStudent(student);
       return CreatedResponseStudent.builder ().studentId ( id.toString () ).build ();
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PutMapping("/students/{id}")
    public void update(@RequestBody @Valid Student student, @PathVariable("id") String studentId){
        studentService.updateStudent(student, studentId);
    }
    @DeleteMapping("/{}/students/{id}")
    public void delete(@PathVariable("id") UUID studentId){
        studentService.deleteStudent(studentId);
    }
}
