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

    when ( groupService.addGroup ( group ) )
            .thenThrow ( new GroupDuplicateException ("Group with this name already exist") );


    mockMvc.perform ( MockMvcRequestBuilders.post ( "/groups" )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                    {
                      "name" : "MK-21"
                    }""" ) )
            .andExpect (status ().isBadRequest () ).andExpect ( content ()
                    .json ( "{\"error\":\"DUPLICATED GROUP\"," +
                            "\"code\":null,\"message\":\"Group with this name already exist\"," +
                            "\"invalidValue\":null,\"field\":null}" ) );
  }

  @Test
  void ifCreateGroupWithNotValidNameThenGroupIsNotCreated () throws Exception {
    var groupId = UUID.randomUUID ().toString ();
    when ( groupService.addGroup ( any ( Group.class ) ) ).thenReturn ( groupId );

    mockMvc.perform ( MockMvcRequestBuilders.post ( "/groups" )
                    .contentType ( MediaType.APPLICATION_JSON )
                    .content ( """
                    {
                      "name" : "MK22"
                    }""" ) )
            .andExpect (status ().isBadRequest () )
            .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
            .andExpect ( content ().json ( """
                    {
                      "error": null,
                      "code": "400",
                      "message": "INVALID EXCEPTION",
                      "invalidValue": "MK22",
                      "field": "name"
                    }""" ) );
  }

  @Test
  void ifPutGroupThenNewGroupReplaceOldGroup () throws Exception {
    var groupId =  UUID.randomUUID ().toString ();
    Group group = new Group ();
    group.setName ( "MK-22" );
    group.setStudentIds ( List.of ("12", "34") );
    doNothing().when ( groupService).updateGroup ( group, groupId );

    mockMvc.perform ( MockMvcRequestBuilders.put ( "/groups/{id}", groupId )
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
    verify ( groupService ).updateGroup ( group, groupId );
  }

  @Test
  void ifPutGroupOnInvalidPathThenThrowNotFoundException () throws Exception {
    Group group = new Group ();
    group.setName ( "MK-22" );
    group.setGroupId ( UUID.randomUUID ().toString () );
    group.setStudentIds ( List.of ("12", "34") );

    doThrow (new GroupNotFoundException ("GROUP NOT FOUND") ).when ( groupService)
            .updateGroup ( any ( Group.class), eq ( group.getGroupId () ) );

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
            .andExpect (status ().isNotFound () )
            .andExpect ( content ()
                    .json ( "{\"error\":null,\"code\":null,\"message\":\"GROUP NOT FOUND\"" +
                            ",\"invalidValue\":null,\"field\":null}" ) );
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