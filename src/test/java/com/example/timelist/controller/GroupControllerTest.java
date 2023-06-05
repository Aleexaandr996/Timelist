package com.example.timelist.controller;

import com.example.timelist.beans.Group;
import com.example.timelist.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    GroupService groupService;

    @InjectMocks
    GroupController groupController;

    @Test
    void create() {
        Group group = new Group();
        group.setName("MK-21");

//        When
        groupController.create(group);

//        Then
        verify(groupService).addGroup(group);
    }

    @Test
    void getGroups() {
        List<Group> expectedGroups = new ArrayList<>();

        when(groupService.getGroups()).thenReturn(expectedGroups);

        //When
        List<Group> groups = groupController.getGroups();

        //Then
        assertThat(groups).isSameAs(expectedGroups);
    }

    @Test
    void update() {
        Group group = new Group();
        group.setName("MK-21");
        String groupId = UUID.randomUUID().toString();
//         When
        groupController.update(group, groupId);

//        Then
        verify(groupService).updateGroup(group, groupId);
    }

    @Test
    void delete() {
//        Given
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        group.setName("MK-21");

        groupController.delete(group);

        verify(groupService).deleteGroup(group);
    }
}