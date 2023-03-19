package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.error.StudentDuplicateException;
import com.example.timelist.error.StudentInGroupException;
import com.example.timelist.persistence.InMemoryStorage;
import com.example.timelist.beans.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final InMemoryStorage storage;

    public void addStudent ( Student student){
        log.info("Create student name={} age={} ", student.getName(), student.getAge());
        student.setId(UUID.randomUUID().toString());
        checkStDuplicateEx(student);
        storage.add(student);
    }

    public List<Student> getStudents(){
        return storage.getStudents();
    }

    public void updateStudent (Student student, String studentId){
        log.info("update student name={} age={}", student.getName(), student.getAge());
        storage.updateStudent(student, studentId);
        checkStDuplicateEx(student);
    }

    public void deleteStudent (Student student){
        log.info("Delete student name={} Id={}", student.getName(), student.getId());
        storage.deleteStudent(student);
    }

    public void checkStDuplicateEx(Student student){
        log.info("checkStDuplicateEx");
        for (Student value : storage.getStudents()) {
            if (value.getId().equals(student.getId())) {
                if (value.getName().equals(student.getName())) {
                    if (value.getAge() == (student.getAge())) {
                        throw new StudentDuplicateException();
                    }
                }
            }
        }
    }
    public void checkStMoreGroup(Student student){
        log.info("Check Student More Group");
        for (Group group : storage.getGroups()) {
            for (int j = 0; j < group.getStudentId().size(); j++) {
                if (group.getStudentId().get(j).equals(student.getId())) {
                    throw new StudentInGroupException();
                }
            }
        }
    }
}
