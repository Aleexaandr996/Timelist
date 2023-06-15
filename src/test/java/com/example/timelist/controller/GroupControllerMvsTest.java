package com.example.timelist.controller;

import com.example.timelist.beans.Group;
import com.example.timelist.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
class GroupControllerMvcTest {
@MockBean
    private GroupService groupService;
    @Autowired
    private GroupController groupController;
    @Autowired
    private MockMvc mockMvc;

@Test
    void createMockMvc(){
    assertThat(mockMvc).isNotNull();
    }

    void WhenDoGetGroupsThenReturnAllGroups(){

        Group group = new Group();
        group.setName("MK-21");
        String groupId = UUID.randomUUID().toString();
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(group);

        when(groupService.getGroups()).thenReturn(expectedGroups);

        mockMvc.perform(MockHttpServletRequestBuilder.get()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\": \"MK-21\", \"groupId\": }"));
    }

}