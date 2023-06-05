package com.example.timelist.persistence;

import com.example.timelist.beans.Day;
import com.example.timelist.beans.Group;
import com.example.timelist.beans.Lecture;
import com.example.timelist.beans.Student;
import com.example.timelist.error.GroupNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InMemoryStorage implements Storage {

    public List<Day> getDays() {
        return days;
    }

    private final List<Day> days = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final List<Lecture> lectures = new ArrayList<>();



    public List<Student> getStudents() {
        return students;
    }

    @Override
    public void add(Student student) {
        students.add(student);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void addLectureInDay(Day day) {
        for (Lecture lecture : lectures) {
            if (day.getDate() == lecture.getDateTime().toLocalDate()) {
                day.getTimeListDay().add(lecture.getId());
            }
        }
    }

    public Lecture updateLecture(Lecture lecture, String lectureId) {
        for (int i = 0; i < getDays().size(); i++) {
            if (lectures.get(i).getId().equals(lectureId)) {
                lectures.set(i, lecture);
            }
        }
        return null;
    }

    public Lecture deleteLecture(String lectureId) {
        for (int i = 0; i < getLectures().size(); i++) {
            if (lectures.get(i).getId().equals(lectureId)) {
                lectures.remove(lectures.get(i));
            }
        }
        return null;
    }

    public Day updateDay(Day day) {
        for (int i = 0; i < getDays().size(); i++) {
            if (days.get(i).getId().equals(day.getId())) {
                days.set(i, day);
            }
        }
        return null;
    }

    public Day deleteDay(Day day) {
        for (int i = 0; i < getDays().size(); i++) {
            if (days.get(i).getId().equals(day.getId())) {
                days.remove(days.get(i));
            }
        }
        return null;
    }

    public Student updateStudent(Student student, String studentId) {
        for (int i = 0; i < getStudents().size(); i++) {
            if (students.get(i).getId().equals(studentId)) {
                students.set(i, student);
            }
        }
        return null;
    }

    public Student deleteStudent(Student student) {
        for (int i = 0; i < getStudents().size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.remove(students.get(i));
            }
        }
        return null;
    }

    public void updateGroup(Group group, String groupId) {
        for (int i = 0; i < getGroups().size(); i++) {
            if (groups.get(i).getGroupId().equals(groupId)) {
                groups.set(i, group);
                return;
            }
        }
        throw new GroupNotFoundException();
    }

    public void deleteGroup(Group group) {
        for (int i = 0; i < getGroups().size(); i++) {
            if (groups.get(i).getGroupId().equals(group.getGroupId())) {
                groups.remove(groups.get(i));
            }
        }
    }


    @Override
    public void addGroup(Group group) {
        groups.add(group);
    }

    public void add(Lecture lecture) {
        lectures.add(lecture);
    }
}
