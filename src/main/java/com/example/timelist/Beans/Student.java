package com.example.timelist.Beans;

import javax.validation.constraints.*;

public class Student {
    private String id;
    @NotBlank
    @Size(min = 1, max = 15)
    private String name ;
    @NotNull
    @Positive
    @Min(14)
    @Max(99)
    private int age;


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


}
