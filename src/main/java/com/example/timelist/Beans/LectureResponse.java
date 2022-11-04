package com.example.timelist.Beans;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class LectureResponse {
    private  int id;

    @NotBlank
    @Size(min = 1, max = 15)
    private  String name;
    @NotBlank
    @Size(min = 1, max = 15)
    private String lecturer;
    @NotNull
    @Size(min = 1, max = 5)
    private String room;
    @NotNull
    @Future
    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
