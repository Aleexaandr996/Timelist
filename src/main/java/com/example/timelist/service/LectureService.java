package com.example.timelist.service;

import com.example.timelist.beans.Lecture;
import com.example.timelist.error.LectureRoomException;
import com.example.timelist.persistence.InMemoryStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {
    private final InMemoryStorage storage;

    public Lecture addLecture(Lecture lecture) {
        log.info("Create lecture name={} room={} dateTime={} lector={}", lecture.getName(), lecture.getRoom(),
                lecture.getDateTime(), lecture.getLectorName());
        lecture.setId(UUID.randomUUID().toString());
        checkRoom(lecture);
        storage.add(lecture);
        return lecture;
    }

    public List<Lecture> getLectures(){
        return storage.getLectures();
    }

    public void updateLecture (Lecture lecture, String lectureId) {
        log.info("Update lecture");
        checkRoom(lecture);
        storage.updateLecture(lecture, lectureId);
    }

    public void  deleteLecture (Lecture lecture) {
        log.info("Delete lecture name={} id={}", lecture.getName(), lecture.getId());
        storage.deleteLecture(lecture.getId());
    }

    public void checkRoom (Lecture lecture){
        log.info("Check Room");
        for (Lecture existingLecture : storage.getLectures()) {
            if (isRoomBooked(existingLecture, lecture)) {
                throw new LectureRoomException();
            }
        }
    }

    private static boolean isRoomBooked(Lecture existingLecture, Lecture newLecture) {
        return Objects.equals(existingLecture.getRoom(), newLecture.getRoom()) && existingLecture.getDateTime().isEqual(newLecture.getDateTime());
    }
}
