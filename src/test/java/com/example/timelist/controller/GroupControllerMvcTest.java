package com.example.timelist.controller;


import com.example.timelist.beans.Group;
import com.example.timelist.error.GroupDuplicateException;
import com.example.timelist.error.GroupNotFoundException;
import com.example.timelist.service.GroupService;
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
import static org.mockito.Mockito.*;
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
  void WhenDoGetGroupsThenReturnAllGroups () throws Exception {

    Group group = new Group ();
    group.setName ( "MK-21" );
    group.setGroupId ( UUID.randomUUID ().toString () );
    List<Group> expectedGroups = new ArrayList<> ();
    expectedGroups.add ( group );

    when ( groupService.getGroups () ).thenReturn ( expectedGroups );

    mockMvc.perform ( MockMvcRequestBuilders.get ( "/groups" ) )
            .andExpect ( status ().isOk () )
            .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
            .andExpect ( content ().json ( "[" +
                    "{\n"
                    + "  \"name\": \"MK-21\",\n"
                    + "  \"groupId\": \"" + group.getGroupId () + "\"\n"
                    + "}" +
                    "]" ) );
  }

  @Test
  void ifCreateGroupThenGroupCreated () throws Exception {
    var groupId = UUID.randomUUID ().toString ();
    when ( groupService.addGroup ( any ( Group.class ) ) ).thenReturn ( groupId );

    mockMvc.perform ( MockMvcRequestBuilders.post ( "/groups" )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                    {
                      "name" : "MK-21"
                    }""" ) )
                    .andExpect (status ().isOk () )
                    .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                    .andExpect ( content ().json ( "{\n" +
                                                   " \"id\" : \"" + groupId + "\"\n" +
                                                   "  \n" +
                                                   "}" ) );
  }

  @Test
  void ifCreateDuplicatedGroupThenThrowDuplicateException () throws Exception {
    Group group = new Group ();
    group.setName ( "MK-21" );
    groupService.addGroup ( group );

    var groupId = UUID.randomUUID ().toString ();
    doThrow ( new GroupDuplicateException () ).when ( groupService.addGroup ( any ( Group.class ) ) );

    mockMvc.perform ( MockMvcRequestBuilders.post ( "/groups" )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                    {
                      "name" : "MK-21"
                    }""" ) )
            .andExpect (status ().isBadRequest () );

  }

  @Test
  void ifCreateGroupWithNotValidNameThenGroupIsNotCreated () throws Exception {
    var groupId = UUID.randomUUID ().toString ();
    when ( groupService.addGroup ( any ( Group.class ) ) ).thenReturn ( groupId );

    mockMvc.perform ( MockMvcRequestBuilders.post ( "/groups" )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                    {
                      "name" : "MK21"
                    }""" ) )
            .andExpect (status ().isBadRequest () )
            .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
            .andExpect ( content ().json ("{\n" +
                    "  \"error\": null,\n" +
                    "  \"code\": \"400\",\n" +
                    "  \"massage\": \"INVALID EXCEPTION\",\n" +
                    "  \"invalidValue\": \"MK21\",\n" +
                    "  \"field\": \"name\"\n" +
                    "}") );
  }

  @Test
  void ifPutGroupThenNewGroupReplaceOldGroup () throws Exception {
    Group group = new Group ();
    group.setName ( "MK-21" );
    group.setGroupId ( UUID.randomUUID ().toString () );
    doNothing().when ( groupService).updateGroup ( group, group.getGroupId () );

    mockMvc.perform ( MockMvcRequestBuilders.put ( "/groups/{id}", group.getGroupId () )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                            {
                              
                              "name": "MK-22",
                              "studentIds": [
                                "12",
                                "34"
                              ]
                            }""" ) )
            .andExpect (status ().isOk () );

  }

  @Test
  void ifPutGroupOnInvalidPathThenThrowNotFoundException () throws Exception {
    var groupId = UUID.randomUUID ();
    doThrow (new GroupNotFoundException ("GROUP NOT FOUND") ).when ( groupService).updateGroup ( any ( Group.class), groupId.toString () );

    mockMvc.perform ( MockMvcRequestBuilders.put ( "/groups/{id}", groupId )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                            {
                              "groupId": "31440ed6-0d48-4307-8165-8a0b0d509a10",
                              "name": "MK-22",
                              "studentIds": [
                                "12",
                                "34"
                              ]
                            }""" ) )
            .andExpect (status ().isNotFound () );

  }

  @Test
  void ifDeleteGroupThenGivenGroupRemove () throws Exception {
//        Given
    var groupId = UUID.randomUUID ();
    doNothing().when ( groupService).deleteGroup ( groupId );

    mockMvc.perform ( MockMvcRequestBuilders.delete ( "/groups/{id}", groupId ) )
            .andExpect ( MockMvcResultMatchers.status ().isOk () );

  }

}