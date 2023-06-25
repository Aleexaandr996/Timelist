package com.example.timelist.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Lecture {
    private String id;
    @NotBlank
    @Size(min = 1, max = 25)
    private String name;

    private List<String> groupIds;
    @NotBlank
    @Size(min = 1, max = 15)
    private String lectorName;
    @Pattern(regexp = "\\d{1,3}")
    private String room;
    @Future
    private LocalDateTime dateTime;
}
