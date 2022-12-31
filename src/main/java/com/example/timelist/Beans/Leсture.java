package com.example.timelist.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Leсture {
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
