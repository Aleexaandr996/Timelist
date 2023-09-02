package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Student;
import com.example.timelist.error.StudentSizeInGroupException;
import com.example.timelist.error.StudentDuplicateException;
import com.example.timelist.error.StudentDuplicateInGroupException;
import com.example.timelist.persistence.InMemoryStorage;
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

    public UUID addStudent (Student student){
        log.info("Create student name={} age={} ", student.getName(), student.getAge());
        UUID id = UUID.randomUUID();
        student.setStudentId (id);
        checkStudentDuplicate(student);
        storage.add(student);
        return id;
    }

    public List<Student> getStudents(){
        return storage.getStudents();
    }

    public void updateStudent (Student student, UUID studentId){
        log.info("update student name={} age={}", student.getName(), student.getAge());
        checkStudentDuplicate(student);
        storage.updateStudent(student, studentId);
    }

    public void deleteStudent (UUID studentId){
        log.info("Delete student  Id={}", studentId);
        storage.deleteStudent(studentId);
    }

    public void checkStudentDuplicateInGroup(Student student, Group group){
        for (String id : group.getStudentIds()){
            if(student.getName().equals(findStudentName(id))){
                throw new StudentDuplicateInGroupException ();
            };
        }
    }

    private String findStudentName( String id){
        var stId = storage.getStudents().stream().filter(student -> student.getStudentId ().equals(id)).
                findFirst();
        return stId.map(Student::getName).orElse(null);
    }

    public void sizeStudentInGroup (Group group){
        if (group.getStudentIds().size() > 20 ){
            throw new StudentSizeInGroupException();
        }
    }

    public void checkStudentDuplicate(Student student){
        log.info("checkStudentDuplicateEx");
        for (Student value : storage.getStudents()) {
            if (value.getName().equals(student.getName()) &&
               (value.getAge() == (student.getAge()))) {
                throw new StudentDuplicateException();
            }
        }
    }
}
