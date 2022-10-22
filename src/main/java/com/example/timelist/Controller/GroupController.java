package com.example.timelist.Controller;

import com.example.timelist.Beans.Day;
import com.example.timelist.Beans.Group;
import com.example.timelist.Beans.Storage;
import com.example.timelist.Beans.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class GroupController {


    @Autowired
    private Storage storage;


    @PostMapping("/groups")
    public void create(@RequestBody @Valid Group group){
        group.setGroupId(UUID.randomUUID().toString());
        storage.getGroups().add(group);
    }

    @GetMapping("/groups")
    public List<Group> getGroups(){
        return storage.getGroups();
    }

    @PutMapping("/groups/{id}")
    public void update(@RequestBody Group group, @PathVariable("id") String groupId){
        group.setGroupId(groupId);
        storage.updateGroup(group);
    }
    @DeleteMapping("/groups")
    public void delete(@RequestBody Group group){
        storage.deleteGroup(group);
    }



}
