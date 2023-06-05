package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Student;
import com.example.timelist.error.GroupSizeStudentException;
import com.example.timelist.error.StudentAlreadyHaveGroupException;
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
            storage.getGroups().get(k).getStudentId().add(storage.getStudents().get(j).getId());
        }
    }

    public void checkStudentDuplicateInGroup(Student student, Group group){
        for (int i = 0; i < group.getStudentId().size(); i++){
            if(student.getId().equals(group.getStudentId().get(i))){
                throw new StudentInGroupException();
            };
        }
    }

    public void sizeStudentInGroup (Group group){
        if (group.getStudentId().size() > 20 ){
            throw new GroupSizeStudentException();
        }
    }

    public void checkStDuplicateEx(Student student){
        log.info("checkStDuplicateEx");
        for (Student value : storage.getStudents()) {
            if (value.getName().equals(student.getName())) {
                if (value.getAge() == (student.getAge())) {
                    throw new StudentDuplicateException();
                }
            }

        }
    }

    public void checkStMoreOneGroup (Group stGroup, String studentId){
        for (Group checkGroup : storage.getGroups() ){
            for (String idInList : checkGroup.getStudentId()){
                if(idInList.equals(studentId)){
                    if(checkGroup != stGroup){
                        throw new StudentAlreadyHaveGroupException();
                    }
                }
            }
        }
    }
}
