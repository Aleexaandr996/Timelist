package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.error.GroupDuplicateException;
import com.example.timelist.persistence.InMemoryStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final InMemoryStorage storage;

    public UUID addGroup (Group group){
        log.info("Create group name={}", group.getName());
        group.setGroupId(UUID.randomUUID());
        findDuplicateGroup(group);
        storage.addGroup(group);
        return group.getGroupId ();
    }

    public List<Group> getGroups(){
        return storage.getGroups();
    }

    public void updateGroup (Group group, UUID groupId){
        log.info("Update group name={} groupId={}",group.getName(), group.getGroupId());
        group.setGroupId(groupId);
        findDuplicateGroup(group);
        storage.updateGroup(group, groupId);

    }

    public void deleteGroup (UUID groupId){
        log.info("Delete group id={}", groupId);
        storage.deleteGroup(groupId);
    }

    private void findDuplicateGroup(Group group) {
        log.info("Find Duplicate Group");
        for (int i = 0; i < getGroups().size(); i++) {
            if (storage.getGroups().get(i).getName().equals(group.getName())) {
                Group duplicateGr = storage.getGroups().get(i);
                log.warn("Group with name = [{}] id = [{}] already exist", duplicateGr.getGroupId(),
                        duplicateGr.getName());
                throw new GroupDuplicateException("GROUP WITH NAME: "+group.getName ()+" ALREADY EXIST");
            }
        }
    }
}
