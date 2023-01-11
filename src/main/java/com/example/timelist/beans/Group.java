package com.example.timelist.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
public class Group {
    private String groupId;

    @Pattern(regexp = "[A-Z]{2}-\\d{2}")
    private String name;
    private List<String> studentId;

    public void addStudent(Student student) {
        getStudentId().add(student.getId());
    }
}
