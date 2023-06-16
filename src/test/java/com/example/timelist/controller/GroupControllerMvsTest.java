package com.example.timelist.controller;


import com.example.timelist.beans.Group;
import com.example.timelist.service.GroupService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
class GroupControllerMvcTest {

  @MockBean
  private GroupService groupService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void createMockMvc() {
    assertThat(mockMvc).isNotNull();
  }

  @Test
  void WhenDoGetGroupsThenReturnAllGroups() throws Exception {

    Group group = new Group();
    group.setName("MK-21");
    group.setGroupId(UUID.randomUUID().toString());
    List<Group> expectedGroups = new ArrayList<>();
    expectedGroups.add(group);

    when(groupService.getGroups()).thenReturn(expectedGroups);

    mockMvc.perform(MockMvcRequestBuilders.get("/groups")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[" +
                "{\n"
            + "  \"name\": \"MK-21\",\n"
            + "  \"groupId\": \"" + group.getGroupId() + "\"\n"
            + "}" +
                "]"));
  }

@Test
  void ifCreateGroupThenGroupCreated() throws Exception {
  Group group = new Group();
  group.setName("MK-21");
  group.setGroupId(UUID.randomUUID().toString());
//        When
    groupService.addGroup(group);

//        Then
    verify(groupService).addGroup(group);

  mockMvc.perform(MockMvcRequestBuilders.post("/groups"))
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json("{\n"
                  + "  \"name\": \"MK-21\",\n"
                  + "  \"groupId\": \"" + group.getGroupId() + "\"\n"
                  + "}"))
          .andExpect(MockMvcResultMatchers.status().isCreated())
          .andExpect(MockMvcResultMatchers.header().exists("Location"))
          .andExpect(MockMvcResultMatchers.header()
                  .string("Location", Matchers.containsString("MK-21")));

  verify(groupService).addGroup(any(Group.class));
  }

//  @Test
//  void IfUpdateGroupThenNewGroupSaveOnPlaceOldGroup() {
//    Group group = new Group();
//    group.setName("MK-21");
//    String groupId = UUID.randomUUID().toString();
////         When
//    groupService.updateGroup(group, groupId);
//
////        Then
//    verify(groupService).updateGroup(group, groupId);
//  }

  @Test
  void ifDeleteGroupThenGivenGroupRemove() throws Exception {
//        Given
    List<Group> groups = new ArrayList<>();
    Group group = new Group();
    group.setName("MK-21");
    group.setGroupId(UUID.randomUUID().toString());
    groups.add(group);

    groupService.deleteGroup(group);

    verify(groupService).deleteGroup(group);

    mockMvc.perform(MockMvcRequestBuilders.delete("/groups"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\n"
                    + "  \"name\": \"MK-21\",\n"
                    + "  \"groupId\": \"" + group.getGroupId() + "\"\n"
                    + "}"))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

}