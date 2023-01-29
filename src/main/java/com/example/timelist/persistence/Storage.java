package com.example.timelist.persistence;

import com.example.timelist.beans.Day;
import com.example.timelist.beans.Group;
import com.example.timelist.beans.Lecture;
import com.example.timelist.beans.Student;

import java.util.List;

public interface Storage {
     void addGroup(Group group);

    public List<Group> getGroups();

    void updateGroup (Group group);

    public void deleteGroup (Group group);

    public List<Day> getDays();

    public void addLectureInDay(Day day);

    public Day updateDay(Day day);

    public Day deleteDay(Day day);

    public List<Lecture> getLectures();

    public Lecture updateLeсture(Lecture lecture);

    public Lecture deleteLeсture(Lecture lecture);

    public List<Student> getStudents();
    void add(Student student);
    public Student updateStudent(Student student);
    public Student deleteStudent(Student student);
}
