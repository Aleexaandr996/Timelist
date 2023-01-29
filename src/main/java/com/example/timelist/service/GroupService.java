package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.persistence.InMemoryStorage;
import com.example.timelist.persistence.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final InMemoryStorage storage;
    private final GroupService groupService;
    public void addGroup (Group group){
        group.setGroupId(UUID.randomUUID().toString());
        storage.addGroup(group);
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
