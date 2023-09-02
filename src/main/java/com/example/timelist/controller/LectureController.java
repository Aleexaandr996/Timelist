package com.example.timelist.controller;

import com.example.timelist.beans.Lecture;
import com.example.timelist.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/lectures")
    public CreatedResponseLecture create(@RequestBody @Valid Lecture lecture) {
        UUID lectureId = lectureService.addLecture(lecture);
        return CreatedResponseLecture
                .builder()
                .lectureId ( lectureId )
                .build();
    }

    @GetMapping("/lectures")
    public List<Lecture> getLectures(){
        return lectureService.getLectures();
    }

    @PutMapping("/lectures/{id}")
    public void update(@RequestBody @Valid Lecture lecture, @PathVariable("id") UUID lectureId){
       lectureService.updateLecture(lecture, lectureId);
    }
    @DeleteMapping("/lectures/{id}")
    public void delete(@PathVariable("id") UUID lectureId){
        lectureService.deleteLecture(lectureId);
    }
}
