package com.example.timelist.Beans;

import com.example.timelist.Error.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Storage {

    public List<Day> getDays() {
        return days;
    }

    private final List<Day> days = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final List<Leсture> lectures = new ArrayList<>();


    public List<Student> getStudents() {
        return students;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Leсture> getLectures() {
        return lectures;
    }


    public void addLectureInDay(Day day) {
        List lectureInDay = new ArrayList();
        for (Leсture leсture : lectures) {
            if (day.getDate() == leсture.getDateTime().toLocalDate()) {
                day.getTimeListDay().add(leсture.getId());
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

    public Leсture updateLeсture(Leсture leсture) {
        for (int i = 0; i < getDays().size(); i++) {
            if (lectures.get(i).getId().equals(leсture.getId())) {
                lectures.set(i, leсture);
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

    public Leсture deleteLeсture(Leсture leсture) {
        for (int i = 0; i < getLectures().size(); i++) {
            if (lectures.get(i).getId().equals(leсture.getId())) {
                lectures.remove(lectures.get(i));
            }
        }
        return null;
    }

    public void checkRoom (Leсture leсture){
        for (int i = 0; i < lectures.size(); i++){
            if(lectures.get(i).getRoom() == leсture.getRoom() ){
               if(lectures.get(i).getDateTime().compareTo(leсture.getDateTime())==0){
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

    public Group deleteGroup(Group group) {
        for (int i = 0; i < getGroups().size(); i++) {
            if (groups.get(i).getGroupId().equals(group.getGroupId())) {
                groups.remove(groups.get(i));
            }
        }
        return null;
    }


}
