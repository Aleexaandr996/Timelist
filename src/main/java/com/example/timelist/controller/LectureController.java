package com.example.timelist.controller;

import com.example.timelist.beans.Leсture;
import com.example.timelist.beans.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class LectureController {

    @Autowired
    private Storage storage;

    @PostMapping("/lectures")
    public Leсture create(@RequestBody Leсture lecture) {

        lecture.setId(UUID.randomUUID().toString());
        storage.getLectures().add(lecture);
        return lecture;
    }

    @GetMapping("/lectures")
    public List<Leсture> getLectures(){
        return storage.getLectures();
    }

    @PutMapping("/lectures/{id}")
    public void update(@RequestBody Leсture leсture, @PathVariable("id") String lectureId){
        leсture.setId(lectureId);
        storage.checkRoom(leсture);
        storage.updateLeсture(leсture);
    }
    @DeleteMapping("/lectures")
    public void delete(@RequestBody Leсture leсture){
        storage.deleteLeсture(leсture);
    }

}
