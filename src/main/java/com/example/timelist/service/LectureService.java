package com.example.timelist.service;

import com.example.timelist.beans.Lecture;
import com.example.timelist.beans.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LectureService {
    private final Storage storage;

    public Lecture addLecture(Lecture lecture) {
        lecture.setId(UUID.randomUUID().toString());
        storage.getLectures().add(lecture);
        return lecture;
    }

    public List<Lecture> getLectures(){
        return storage.getLectures();
    }

    public void updateLecture (Lecture lecture, String lectureId) {
        lecture.setId(lectureId);
        storage.checkRoom(lecture);
        storage.updateLeсture(lecture);
    }

    public void  deleteLecture (Lecture lecture) {
        storage.deleteLeсture(lecture);
    }
}
