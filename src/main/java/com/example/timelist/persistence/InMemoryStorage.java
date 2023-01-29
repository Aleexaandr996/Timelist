package com.example.timelist.persistence;

import com.example.timelist.beans.Day;
import com.example.timelist.beans.Group;
import com.example.timelist.beans.Lecture;
import com.example.timelist.beans.Student;
import com.example.timelist.error.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
        checkStDuplicateEx(student);
        students.add(student);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }


    public void addLectureInDay(Day day) {
        List lectureInDay = new ArrayList();
        for (Lecture lecture : lectures) {
            if (day.getDate() == lecture.getDateTime().toLocalDate()) {
                day.getTimeListDay().add(lecture.getId());
            }
        }
    }

    public void divideStudents() {
        int i = students.size() / groups.size();
        int l = i;
        int k = 0;

        for (int j = 0; j < getStudents().size(); j++) {

            if (j == i) {
                k++;
                i = i + l;
            }
            if (groups.get(k) == null) {
                k--;
            }

            checkStudentDuplicateInGroup(students.get(j), groups.get(k));
            groups.get(k).getStudentId().add(students.get(j).getId());

        }
    }

    public void checkStudentDuplicateInGroup(Student student, Group group){
        for (int i = 0; i < group.getStudentId().size(); i++){
            student.getId().equals(group.getStudentId().get(i));
        }
    }

    public Lecture updateLeсture(Lecture lecture) {
        checkRoom(lecture);
        for (int i = 0; i < getDays().size(); i++) {
            if (lectures.get(i).getId().equals(lecture.getId())) {
                lectures.set(i, lecture);
                ;
            }
        }
        return null;
    }

    public void sizeStudentInGroup (Group group){
        if (group.getStudentId().size() > 20 ){
            throw new GroupSizeStudentException();
        }
    }

    public void findDuplicateStudent (Student student, Group group){

        for (int i = 0; i < group.getStudentId().size(); i++ ){
           if( group.getStudentId().get(i).equals(student.getId())){
               throw new GroupSizeStudentException();
           }
        }
    }

    public Lecture deleteLeсture(Lecture lecture) {
        for (int i = 0; i < getLectures().size(); i++) {
            if (lectures.get(i).getId().equals(lecture.getId())) {
                lectures.remove(lectures.get(i));
            }
        }
        return null;
    }

    public void checkRoom (Lecture lecture){
        for (int i = 0; i < lectures.size(); i++){
            if(lectures.get(i).getRoom() == lecture.getRoom() ){
               if(lectures.get(i).getDateTime().compareTo(lecture.getDateTime())==0){
                   throw new LectureRoomException();
                }
            }
        }
    }

    public Day updateDay(Day day) {
        for (int i = 0; i < getDays().size(); i++) {
            if (days.get(i).getId().equals(day.getId())) {
                days.set(i, day);
                ;
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

    public Student updateStudent(Student student) {
        for (int i = 0; i < getStudents().size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
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

    public void checkStDuplicateEx(Student student){
        for (int i = 0; i < students.size(); i++){
            if (students.get(i).getId().equals(student.getId())) {
                if (students.get(i).getName().equals(student.getName())) {
                    if (students.get(i).getAge() == (student.getAge())) {
                        throw new StudentDuplicateException();
                    }
                }
            }
        }
    }

    public void checkStMoreGroup(Student student){
        for (int i = 0; i < groups.size(); i++){
            for (int j = 0; j < groups.get(i).getStudentId().size(); j++){
                if (groups.get(i).getStudentId().get(j).equals(student.getId())){
                    throw new StudentInGroupException();
                }
            }
        }
    }

    public void updateGroup(Group group) {
        for (int i = 0; i < getGroups().size(); i++) {
            if (groups.get(i).getGroupId().equals(group.getGroupId())) {
                groups.set(i, group);
                return;
            }
        }
        throw new GroupNotFoundException();
    }

    public void findDuplicateGroup(Group group) {
        for (int i = 0; i < getGroups().size(); i++) {
            if (groups.get(i).getName().equals(group.getName())) {
              throw new GroupDuplicateException();
            }
        }

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

}
