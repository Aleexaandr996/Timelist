package com.example.timelist.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class Day {


    private String id;
    @NotNull
    @Future
    private LocalDate date;
    private List<String> timeListDay;
}
