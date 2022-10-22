package com.example.timelist.Beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Leсture {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id ;
    private String name ;

    private List<String> groupIds;
    private String lectorName;
    private String room ;
    private LocalDateTime dateTime ;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
