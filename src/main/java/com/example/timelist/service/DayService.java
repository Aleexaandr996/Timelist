package com.example.timelist.service;

import com.example.timelist.beans.Day;
import com.example.timelist.beans.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DayService {

    private final Storage storage;

    public void addDay(Day day){
        day.setId(UUID.randomUUID().toString());
        storage.getDays().add(day);
        storage.addLectureInDay(day);
    }

    public List<Day> getDays() {
        return storage.getDays();
    }

    public Day updateDay ( Day day, String dayId){
        day.setId(dayId);
        storage.updateDay(day);
        return day;
    }

    public void deleteDay (Day day){
        storage.deleteDay(day);
    }
}
