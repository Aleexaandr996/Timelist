package com.example.timelist.Beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Day {


    private String id;
    private LocalDate date;
    private List<String> timeListDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public List<String> getTimeListDay() {
        return timeListDay;
    }

    public void setTimeListDay(List<String> timeListDay) {
        this.timeListDay = timeListDay;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }









    public void addLectureInDay(Leсture leсture){
        getTimeListDay().add(leсture.getId());

    }
}
