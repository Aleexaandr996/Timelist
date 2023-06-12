package com.example.timelist.controller;

import com.example.timelist.beans.Lecture;
import com.example.timelist.service.LectureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureControllerTest {

    @Mock
    LectureService lectureService;

    @InjectMocks
    LectureController lectureController;

    @Test
    void create() {
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        lecture.setId(UUID.randomUUID().toString());
//         When
        lectureController.create(lecture);

//        Then
        verify(lectureService).addLecture(lecture);
    }

    @Test
    void getLectures() {
//               Given
        List<Lecture> expectedLectures = new ArrayList<>();
        when(lectureService.getLectures()).thenReturn(expectedLectures);
//         When
        List<Lecture> lectures = lectureController.getLectures();

//        Then
        assertThat(lectures).isSameAs(expectedLectures);
    }

    @Test
    void update() {
//        Given
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        String lectureId = UUID.randomUUID().toString();
//        When
        lectureController.update(lecture, lectureId);

//        Then
        verify(lectureService).updateLecture(lecture, lectureId);
    }

    @Test
    void delete() {
//        Given

        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        lecture.setId(UUID.randomUUID().toString());

//         When
        lectureController.delete(lecture);

//        Then
        verify(lectureService).deleteLecture(lecture);
    }
}