package com.example.timelist.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.timelist.beans.Group;
import com.example.timelist.service.GroupService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    String groupId = UUID.randomUUID().toString();
    List<Group> expectedGroups = new ArrayList<>();
    expectedGroups.add(group);

    when(groupService.getGroups()).thenReturn(expectedGroups);

    mockMvc.perform(MockMvcRequestBuilders.get("/groups")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\n"
            + "  \"name\": \"MK-21\",\n"
            + "  \"groupId\": \"" + groupId + "\"\n"
            + "}"));
  }

}