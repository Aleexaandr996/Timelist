package com.example.timelist.Beans;

import java.util.ArrayList;
import java.util.List;

public class Group {




    private String groupId;
    private   String name ;
    private List<String> studentId ;



    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStudentId() {
        return studentId;
    }

    public void setStudentId(List<String> studentId) {
        this.studentId = studentId;
    }

    public void addStudent(Student student){
        getStudentId().add(student.getId());

    }
}
