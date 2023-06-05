package com.example.timelist.service;

import com.example.timelist.beans.Lecture;
import com.example.timelist.error.LectureRoomException;
import com.example.timelist.persistence.InMemoryStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    InMemoryStorage storage;

    @InjectMocks
    LectureService lectureService;
    @Test
    void addLecture() {
//        Given
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(2024,Month.of(9),10,12,30);
        lecture.setId(UUID.randomUUID().toString());
        when(lectureService.getLectures()).thenReturn(new ArrayList<>());
//         When
        lectureService.addLecture(lecture);


//        Then
        verify(storage).add(lecture);
    }

    @Test
    void getLectures() {
//        Given
        List<Lecture> expectedLectures = new ArrayList<>();
        when(storage.getLectures()).thenReturn(expectedLectures);
//         When
        List<Lecture> lectures = lectureService.getLectures();

//        Then
        assertThat(lectures).isSameAs(expectedLectures);
    }

    @Test
    void updateLecture() {
//        Given
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(2024,Month.of(9),10,12,30);
        String lectureId = UUID.randomUUID().toString();
//        When
        lectureService.updateLecture(lecture, lectureId);

//        Then
        verify(storage).updateLecture(lecture, lectureId);
    }

    @Test
    void deleteLecture() {
//               Given

        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(2024,Month.of(9),10,12,30);
        lecture.setId(UUID.randomUUID().toString());

//         When
        lectureService.deleteLecture(lecture);

//        Then
        verify(storage).deleteLecture(lecture.getId());
    }

    @Test
    void checkRoom() {
//           Given
        List<Lecture> lectures = new ArrayList<>();
//          First Lecture
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(2024,Month.of(9),10,12,30);
        lecture.setId(UUID.randomUUID().toString());
        lectures.add(lecture);

        //        When
//        Duplicate Lecture
        Lecture duplicateLecture = new Lecture();
        lecture.setName("Geography");
        lecture.setRoom("25");
        lecture.setLectorName("Leon");
        lecture.setDateTime(2024,Month.of(9),10,12,30);
        lecture.setId(UUID.randomUUID().toString());

        lectureService.addLecture(duplicateLecture);


        Assertions.assertThrows(LectureRoomException.class, () -> {
            lectureService.checkRoom(duplicateLecture);
        });

    }

}