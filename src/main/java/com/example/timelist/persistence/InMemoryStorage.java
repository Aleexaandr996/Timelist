package com.example.timelist.persistence;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Lecture;
import com.example.timelist.beans.Student;
import com.example.timelist.error.GroupNotFoundException;
import com.example.timelist.error.LectureNotFoundException;
import com.example.timelist.error.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InMemoryStorage implements Storage {


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

    public void  updateLecture(Lecture newLecture, UUID lectureId) {
        for (Lecture thisLecture : getLectures()) {
            if (thisLecture.getId().equals(lectureId)) {
                newLecture.setId ( lectureId );
                getLectures().add(newLecture);
            }
        }
        throw new LectureNotFoundException ();
    }

    public void deleteLecture(UUID lectureId) {
        lectures.removeIf ( lecture -> lecture.getId ().equals ( lectureId ) );
    }

    public void updateStudent(Student student, UUID studentId) {
        for (int i = 0; i < getStudents().size(); i++) {
            if (students.get(i).getStudentId ().equals(studentId)) {
                students.set(i, student);
            }
        }
        throw new StudentNotFoundException ( "STUDENT NOT FOUND" );
    }

    public void deleteStudent(UUID studentId) {
        students.removeIf ( student -> student.getStudentId ().equals ( studentId.toString () ) );
    }

    public void updateGroup(Group group, UUID groupId) {
        for (int i = 0; i < getGroups().size(); i++) {
            if (groups.get(i).getGroupId().equals(groupId)) {
                groups.set(i, group);
                return;
            }
        }
        throw new GroupNotFoundException();
    }

    public void deleteGroup(UUID groupId) {
        groups.removeIf ( group -> groupId.equals ( group.getGroupId () ) );
    }


    @Override
    public void addGroup(Group group) {
        groups.add(group);
    }

    public void add(Lecture lecture) {
        lectures.add(lecture);
    }
}
