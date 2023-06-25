package com.example.timelist.persistence;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Lecture;
import com.example.timelist.beans.Student;

import java.util.List;
import java.util.UUID;

public interface Storage {
     void addGroup(Group group);

    public List<Group> getGroups();

    void updateGroup (Group group, String groupId);

    public void deleteGroup (UUID id);

    public List<Lecture> getLectures();

    public void updateLecture(Lecture lecture, String lectureId);

    public  void deleteLecture(UUID id);

    public List<Student> getStudents();
    public void add(Student student);
    public void updateStudent(Student student, String studentId);
    public void deleteStudent(UUID studentId);
}
