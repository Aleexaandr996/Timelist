package com.example.timelist.controller;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Storage;
import com.example.timelist.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class GroupController {


    @Autowired
    private Storage storage;
    private GroupService groupService;


    @PostMapping("/groups")
    public void create(@RequestBody @Valid Group group){
       groupService.add(group);
    }

    @GetMapping("/groups")
    public List<Group> getGroups(){
        return groupService.getGroups();
    }

    @PutMapping("/groups/{id}")
    public void update(@RequestBody @Valid Group group, @PathVariable("id") String groupId){
        groupService.updateGroup(group,groupId);
    }
    @DeleteMapping("/groups")
    public void delete(@RequestBody Group group){
        groupService.deleteGroup(group);
    }



}
