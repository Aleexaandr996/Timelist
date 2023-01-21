package com.example.timelist.controller;

import com.example.timelist.beans.Day;
import com.example.timelist.beans.Storage;
import com.example.timelist.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DayController {

    @Autowired
    private Storage storage;
    private DayService dayService;

    @PostMapping("/days")
    public void create(@RequestBody @Valid Day day){
      dayService.addDay(day);
    }

    @GetMapping("/days")
    public List<Day> getDays(){
        return dayService.getDays();
    }

    @PutMapping("/{id}")
    public @Valid Day update(@RequestBody @Valid Day day, @PathVariable("id") String dayId){
        return dayService.updateDay(day,dayId);
    }
    @DeleteMapping("/days")
    public void delete(@RequestBody Day day){
        dayService.deleteDay(day);
    }
}
