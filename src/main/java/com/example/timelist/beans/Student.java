package com.example.timelist.beans;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Student {
    private UUID studentId;
    @NotBlank
    @Size(min = 1, max = 15)
    private String name ;
    @NotNull
    @Positive
    @Min(14)
    @Max(99)
    private int age;
}
