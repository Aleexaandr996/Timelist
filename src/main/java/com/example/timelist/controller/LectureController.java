package com.example.timelist.controller;

import com.example.timelist.beans.Lecture;
import com.example.timelist.persistence.InMemoryStorage;
import com.example.timelist.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LectureController {

    @Autowired
    private InMemoryStorage storage;
    private LectureService lectureService;

    @PostMapping("/lectures")
    public Lecture create(@RequestBody @Valid Lecture lecture) {
        return lectureService.addLecture(lecture);
    }

    @GetMapping("/lectures")
    public List<Lecture> getLectures(){
        return lectureService.getLectures();
    }

    @PutMapping("/lectures/{id}")
    public void update(@RequestBody @Valid Lecture lecture, @PathVariable("id") String lectureId){
       lectureService.updateLecture(lecture, lectureId);
    }
    @DeleteMapping("/lectures")
    public void delete(@RequestBody Lecture lecture){
        lectureService.deleteLecture(lecture);
    }

}
