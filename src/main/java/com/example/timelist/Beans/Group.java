package com.example.timelist.Beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class Group {


    private String groupId;

    @Pattern("(\d{2})\-(\W{2})")
    @Pattern(pattern = "(\d ={2})(\Q-\E)(\W{2})")
    @NotBlank
    @Size(min = 5,max = 5)
    private String name;
    private List<String> studentId;


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

    public void addStudent(Student student) {
        getStudentId().add(student.getId());

    }
}
