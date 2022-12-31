package com.example.timelist.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
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

}
