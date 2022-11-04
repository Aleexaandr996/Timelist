package com.example.timelist.Beans;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Leсture {




    private String id ;
    @NotBlank
    @Size(min = 1, max = 35)
    private String name ;

    private List<String> groupIds;
    @NotBlank
    @Size(min = 1, max = 15)
    private String lectorName;
    @NotNull
    @Size(min = 1, max = 5)
    private String room ;
    @NotBlank
    @Future
    private LocalDateTime dateTime ;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
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


    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public String getLectorName() {
        return lectorName;
    }

    public void setLectorName(String lectorName) {
        this.lectorName = lectorName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }




}
