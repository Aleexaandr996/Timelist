package com.example.timelist.service;

import com.example.timelist.beans.Group;
import com.example.timelist.error.GroupDuplicateException;
import com.example.timelist.persistence.InMemoryStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

class GroupServiceTest {

    @Mock
    InMemoryStorage storage;
    @InjectMocks
    GroupService groupService;
    @Test
    void ifAddNewGroupThenGroupIsCreated() {
        //Given

        Group group = new Group();
        group.setName("MK-21");
        when(storage.getGroups()).thenReturn(new ArrayList<>());

        //When
        groupService.addGroup(group);

        //Then
        verify(storage).addGroup(group);
        assertThat(group.getGroupId()).isNotNull();
    }

    @Test
    void ifAddNewDuplicateGroupThenGroupIsNotCreated() {
        //Given
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        group.setName("MK-21");
        UUID groupId = UUID.randomUUID();
        group.setGroupId(groupId);
        groups.add(group);

        when(storage.getGroups()).thenReturn(groups);

        Group duplicate = new Group();
        duplicate.setName("MK-21");

        //When
        Assertions.assertThrows(GroupDuplicateException.class, () -> {
            groupService.addGroup(duplicate);
        });

        //Then
        verify(storage,times(0)).addGroup(group);
    }

    @Test
    void ifGetGroupsThenStorageInvoked (){
        List<Group> expectedGroups = new ArrayList<>();

        when(storage.getGroups()).thenReturn(expectedGroups);

        //When
        List<Group> groups = groupService.getGroups();

        //Then
        assertThat(groups).isSameAs(expectedGroups);
    }

    @Test
    void ifDeleteGroupThenGivenGroupRemove(){
//        Given
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        group.setName("MK-21");
        UUID id = UUID.randomUUID();
        group.setGroupId ( id );

        groupService.deleteGroup(id);

        verify(storage).deleteGroup(id);
    }

    @Test
    void IfUpdateGroupThenNewGroupSaveOnPlaceOldGroup(){
        Group group = new Group();
        group.setName("MK-21");
        UUID groupId = UUID.randomUUID();
//         When
        groupService.updateGroup(group, groupId);

//        Then
        verify(storage).updateGroup(group, groupId);
        assertThat(group.getGroupId()).isNotNull();
    }
}