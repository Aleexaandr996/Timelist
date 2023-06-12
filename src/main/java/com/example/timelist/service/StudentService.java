package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Student;
import com.example.timelist.error.GroupSizeStudentException;
import com.example.timelist.error.StudentDuplicateException;
import com.example.timelist.error.StudentInGroupException;
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

    public void addStudent ( Student student){
        log.info("Create student name={} age={} ", student.getName(), student.getAge());
        student.setId(UUID.randomUUID().toString());
        checkStudentDuplicate(student);
        storage.add(student);
    }

    public List<Student> getStudents(){
        return storage.getStudents();
    }

    public void updateStudent (Student student, String studentId){
        log.info("update student name={} age={}", student.getName(), student.getAge());
        checkStudentDuplicate(student);
        storage.updateStudent(student, studentId);
    }

    public void deleteStudent (Student student){
        log.info("Delete student name={} Id={}", student.getName(), student.getId());
        storage.deleteStudent(student);
    }

    public void divideStudents() {
        int i = storage.getStudents().size() / storage.getGroups().size();
        int l = i;
        int k = 0;

        for (int j = 0; j < storage.getGroups().size() * 20 ; j++) {

            if (j == 20) {
                sizeStudentInGroup(storage.getGroups().get(k));
                k++;
                j = 0;
            }
            checkStudentDuplicateInGroup(storage.getStudents().get(j), storage.getGroups().get(k));
            storage.getGroups().get(k).getStudentIds().add(storage.getStudents().get(j).getId());
        }
    }


    public void checkStudentDuplicateInGroup(Student student, Group group){
        for (String id : group.getStudentIds()){
            if(student.getName().equals(findStudentName(id))){
                throw new StudentInGroupException
                        ("Student with this name ["+student.getName()+"] in this group["
                                +group.getName()+"] already exist ");
            };
        }
    }

    private String findStudentName( String id){
        var stId = storage.getStudents().stream().filter(student -> student.getId().equals(id)).
                findFirst();
        return stId.map(Student::getName).orElse(null);
    }

    public void sizeStudentInGroup (Group group){
        if (group.getStudentIds().size() > 20 ){
            throw new GroupSizeStudentException("Maximum number students in group - 20 persons");
        }
    }

    public void checkStudentDuplicate(Student student){
        log.info("checkStudentDuplicateEx");
        for (Student value : storage.getStudents()) {
            if (value.getName().equals(student.getName()) &&
               (value.getAge() == (student.getAge()))) {
                throw new StudentDuplicateException("Student with name = "+student.getName()+" already exist ");
            }
        }
    }
}
