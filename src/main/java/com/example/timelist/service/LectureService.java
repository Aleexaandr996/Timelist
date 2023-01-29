package com.example.timelist.service;

import com.example.timelist.beans.Lecture;
import com.example.timelist.persistence.InMemoryStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final InMemoryStorage storage;

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
        storage.updateLeсture(lecture);
    }

    public void  deleteLecture (Lecture lecture) {
        storage.deleteLeсture(lecture);
    }
}
