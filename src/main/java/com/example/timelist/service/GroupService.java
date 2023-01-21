package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.beans.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupService {
    private Storage storage;
    public void add(Group group){
        group.setGroupId(UUID.randomUUID().toString());
        storage.getGroups().add(group);
    }

    public List<Group> getGroups(){
        return storage.getGroups();
    }

    public void updateGroup (Group group,String groupId){
        group.setGroupId(groupId);
        storage.updateGroup(group);
    }

    public void deleteGroup (Group group){
        storage.deleteGroup(group);
    }
}
