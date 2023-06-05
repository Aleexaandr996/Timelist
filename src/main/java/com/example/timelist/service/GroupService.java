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

    public void addGroup (Group group){
        log.info("Create group name={}", group.getName());
        group.setGroupId(UUID.randomUUID().toString());
        findDuplicateGroup(group);
        storage.addGroup(group);
    }

    public List<Group> getGroups(){
        return storage.getGroups();
    }

    public void updateGroup (Group group,String groupId){
        log.info("Update group name={} groupId={}",group.getName(), group.getGroupId());
        group.setGroupId(groupId);
        storage.updateGroup(group, groupId);
        findDuplicateGroup(group);
    }

    public void deleteGroup (Group group){
        log.info("Delete group name={} id={}", group.getName(), group.getGroupId());
        storage.deleteGroup(group);
    }

    private void findDuplicateGroup(Group group) {
        log.info("Find Duplicate Group");
        for (int i = 0; i < getGroups().size(); i++) {
            if (storage.getGroups().get(i).getName().equals(group.getName())) {
                Group duplicateGr = storage.getGroups().get(i);
                log.warn("Group with name = [{}] id = [{}] already exist", duplicateGr.getGroupId(),
                        duplicateGr.getName());
                throw new GroupDuplicateException();
            }
        }
    }
}
