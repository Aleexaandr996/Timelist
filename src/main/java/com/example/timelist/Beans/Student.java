package com.example.timelist.Beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Student {
    private String id;
    @NotBlank
    @Size(min = 1, max = 15)
    private String name ;
    @NotBlank
    @Size(min = 2, max = 2 )
    private int age;
    private int groupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
