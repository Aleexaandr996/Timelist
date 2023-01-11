package com.example.timelist.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LectureResponse {
    private  int id;

    @NotBlank
    @Size(min = 1, max = 15)
    private  String name;
    @NotBlank
    @Size(min = 1, max = 15)
    private String lecturer;
    @Pattern(regexp = "d{1,3}")
    private String room;
    @NotNull
    @Future
    private LocalDateTime time;


}
