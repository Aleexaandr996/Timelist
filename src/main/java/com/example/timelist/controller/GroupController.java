package com.example.timelist.controller;

import com.example.timelist.beans.Group;
import com.example.timelist.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/groups")
    public CreatedResponseGroup create (@RequestBody @Valid Group group) {
        String groupId = groupService.addGroup ( group );
        return CreatedResponseGroup.builder ()
                .id ( groupId )
                .build ();
    }

    @GetMapping("/groups")
    public List<Group> getGroups(){
        return groupService.getGroups();
    }

    @PutMapping("/groups/{id}")
    public void update(@RequestBody @Valid Group group, @PathVariable("id") String groupId){
        groupService.updateGroup(group,groupId);
    }
    @DeleteMapping("/groups/{id}")
    public void delete(@PathVariable ("id") UUID groupId){
        groupService.deleteGroup(groupId);
    }
}
