package com.example.timelist.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Pattern(regexp = "d{1,3}")
    private String room;
    @NotBlank
    @Future
    private LocalDateTime dateTime;
}
