package com.example.timelist.Controller;

import com.example.timelist.Beans.Day;
import com.example.timelist.Beans.Group;
import com.example.timelist.Beans.Storage;
import com.example.timelist.Beans.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DayController {

    @Autowired
    private Storage storage;

    @PostMapping("/days")
    public void create(@RequestBody Day day){
       day.setId(UUID.randomUUID().toString());
        storage.getDays().add(day);
        storage.addLectureInDay(day);
    }

    @GetMapping("/days")
    public List<Day> getDays(){
        return storage.getDays();
    }

    @PutMapping("/{id}")
    public Day update(@RequestBody Day day, @PathVariable("id") String dayId){
        day.setId(dayId);
        storage.updateDay(day);
        return day;
    }
    @DeleteMapping("/days")
    public void delete(@RequestBody Day day){
        storage.deleteDay(day);
    }
}
