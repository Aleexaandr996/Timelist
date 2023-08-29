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

import java.time.LocalDateTime;
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
    void ifAddNewLectureThenLectureIsCreated() {
//        Given
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        lecture.setId(UUID.randomUUID());
        when(lectureService.getLectures()).thenReturn(new ArrayList<>());
//         When
        lectureService.addLecture(lecture);


//        Then
        verify(storage).add(lecture);
        assertThat(lecture.getId()).isNotNull();
    }

    @Test
    void ifGetLecturesThenStorageInvoked() {
//        Given
        List<Lecture> expectedLectures = new ArrayList<>();
        when(storage.getLectures()).thenReturn(expectedLectures);
//         When
        List<Lecture> lectures = lectureService.getLectures();

//        Then
        assertThat(lectures).isSameAs(expectedLectures);
    }

    @Test
    void IfUpdateLectureThenNewLectureSaveOnPlaceOldLecture() {
//        Given
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        UUID lectureId = UUID.randomUUID();
//        When
        lectureService.updateLecture(lecture, lectureId);

//        Then
        verify(storage).updateLecture(lecture, lectureId);

    }

    @Test
    void ifDeleteLectureThenGivenLectureRemove() {
//               Given

        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        UUID id = UUID.randomUUID();
        lecture.setId(id);

//         When
        lectureService.deleteLecture(id);

//        Then
        verify(storage).deleteLecture(id);
    }

    @Test
    void ifRoomBookedThenNewLectureIsNotCreated() {
//           Given
        List<Lecture> lectures = new ArrayList<>();
//          First Lecture
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        lecture.setId(UUID.randomUUID());
        lectures.add(lecture);

        //        Duplicate Lecture
        Lecture duplicateLecture = new Lecture();
        duplicateLecture.setName("Geography");
        duplicateLecture.setRoom("25");
        duplicateLecture.setLectorName("Leon");
        duplicateLecture.setDateTime(LocalDateTime.of(2024,9,10,12,30));
        duplicateLecture.setId(UUID.randomUUID());

        //        When
        when(storage.getLectures()).thenReturn(lectures);


        Assertions.assertThrows(LectureRoomException.class, () -> {
            lectureService.addLecture(duplicateLecture);
        });

        verify(storage,times(0)).add(duplicateLecture);
    }
}