package com.example.timelist.controller;

import com.example.timelist.beans.Lecture;
import com.example.timelist.error.LectureDuplicateException;
import com.example.timelist.error.LectureNotFoundException;
import com.example.timelist.error.LectureRoomException;
import com.example.timelist.service.LectureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LectureController.class)
class LectureControllerMvcTest {
    @MockBean
    private LectureService lectureService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createMockMvc() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void IfCreateThanLectureIsCreate () throws Exception {
        var lectureId = UUID.randomUUID ();
        when(lectureService.addLecture ( any ( Lecture.class) )).thenReturn ( lectureId );

        mockMvc.perform ( post ( "/lectures" )
                .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name": "Biology2",
                          "groupIds": [  ],
                          "lectorName" : "Ivanna",
                          "room" : "123",
                          "dateTime" : "2023-09-07T10:45:00"

                        }""" ) )
                .andExpect (status().isOk () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ().json ( "{\n" +
                        " \"lectureId\" : \"" + lectureId + "\"\n" +
                        "  \n" +
                        "}" ) );
    }

    @Test
    void IfCreateLectureDuplicateThenThrowDuplicateException () throws Exception {
        Lecture lecture = new Lecture ();
        lecture.setName ( "Biology2" );
        lecture.setLectorName ("Ivana");
        lecture.setRoom ( "123" );
        lecture.setDateTime( LocalDateTime.of(2023,9,7,10,45, 0));


       when (lectureService.addLecture (lecture))
               .thenThrow ( new LectureDuplicateException ( "GROUP WITH NAME: " + lecture.getName () + " ALREADY EXIST" ) );

        mockMvc.perform ( post ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name": "Biology2",
                          "lectorName" : "Ivana",
                          "room" : "123",
                          "dateTime" : "2023-09-07T10:45:00"

                        }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( "{\"error\":\"DUPLICATED LECTURE\",\"code\":null," +
                                "\"message\":\"Lecture with this name already exist at this time and this room\"," +
                                "\"invalidValue\":null,\"field\":null}" ) );
    }

    @Test
    void IfCreateLectureWithInvalidNameThenLectureIsNotCreated () throws Exception {
        var lectureId = UUID.randomUUID ();


        when(lectureService.addLecture ( any ( Lecture.class) )).thenReturn ( lectureId );

        mockMvc.perform ( post ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name" : "",
                          "lectorName" : "Ivana",
                          "room" : "123",
                          "dateTime" : "2023-09-07T10:45:00"

                        }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( """
                                {
                                                      "error": null,
                                                      "code": "400",
                                                      "message": "INVALID EXCEPTION",
                                                      "invalidValue": "",
                                                      "field": "name"
                                                    }""" ) );
    }

    @Test
    void IfCreateLectureWithInvalidLectorNameThenLectureIsNotCreated () throws Exception {
        var lectureId = UUID.randomUUID ();


        when(lectureService.addLecture ( any ( Lecture.class) )).thenReturn ( lectureId );

        mockMvc.perform ( post ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name" : "Biology",
                          "lectorName" : "",
                          "room" : "123",
                          "dateTime" : "2023-09-07T10:45:00"

                        }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( """
                                {
                                                      "error": null,
                                                      "code": "400",
                                                      "message": "INVALID EXCEPTION",
                                                      "invalidValue": "",
                                                      "field": "lectorName"
                                                    }""" ) );
    }

    @Test
    void IfCreateLectureWithInvalidDateThenLectureIsNotCreated () throws Exception {
        var lectureId = UUID.randomUUID ();


        when(lectureService.addLecture ( any ( Lecture.class) )).thenReturn ( lectureId );

        mockMvc.perform ( post ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name" : "Biology",
                          "lectorName" : "Inna",
                          "room" : "123",
                          "dateTime" : "2023-07-02T10:45:00"

                        }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( """
                                {
                                                      "error": null,
                                                      "code": "400",
                                                      "message": "INVALID EXCEPTION",
                                                      "invalidValue": "2023-07-02T10:45",
                                                      "field": "dateTime"
                                                    }""" ) );
    }

    @Test
    void IfCreateLectureWithInvalidRoomThenLectureIsNotCreated () throws Exception {
        var lectureId = UUID.randomUUID ();


        when(lectureService.addLecture ( any ( Lecture.class) )).thenReturn ( lectureId );

        mockMvc.perform ( post ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name" : "Biology",
                          "lectorName" : "Inna",
                          "room" : "12v",
                          "dateTime" : "2023-09-07T10:45:00"

                        }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( "{\"error\":null,\"code\":\"400\"" +
                                ",\"message\":\"INVALID EXCEPTION\"" +
                                ",\"invalidValue\":\"12v\",\"field\":\"room\"}" ) );
    }

    @Test
    void IfCreateLectureWithBookedRoomThenThrowRoomException () throws Exception {
        Lecture lecture = new Lecture ();
        lecture.setName ( "Biology2" );
        lecture.setLectorName ("Ivana");
        lecture.setRoom ( "123" );
        lecture.setDateTime( LocalDateTime.of(2023,9,7,10,45, 0));


        when (lectureService.addLecture (lecture))
                .thenThrow ( new LectureRoomException ( "LECTURE ROOM IS BOOKED ON THIS DAY AND TIME" ) );

        mockMvc.perform ( post ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {

                          "name": "Biology2",
                          "lectorName" : "Ivana",
                          "room" : "123",
                          "dateTime" : "2023-09-07T10:45:00"

                        }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( "{\"error\":\"LECTURE ROOM EXCEPTION\",\"code\":null," +
                                "\"message\":\"LECTURE ROOM IS BOOKED ON THIS DAY AND TIME\"," +
                                "\"invalidValue\":null,\"field\":null}" ) );
    }

    @Test
    void IfGetLecturesThanDoGetAllLectures () throws Exception {
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime( LocalDateTime.of(2024,9,10,12,30));
        lecture.setId ( UUID.randomUUID () );

        List<Lecture> expectedLectures = new ArrayList<> ();
        expectedLectures.add ( lecture );

        when(lectureService.getLectures()).thenReturn(expectedLectures);

        mockMvc.perform ( get ( "/lectures" )
                        .contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect (status().isOk () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ().json ( "[\n" +
                        "  {\n" +
                        "    \"id\": \"" + lecture.getId () + "\",\n" +
                        "    \"name\": \"History\",\n" +
                        "    \"groupIds\": null,\n" +
                        "    \"lectorName\": \"Inna\",\n" +
                        "    \"room\": \"25\",\n" +
                        "    \"dateTime\": \"2024-09-10T12:30:00\"\n" +
                        "  }\n" +
                        "]" ) );
    }

    @Test
    void ifPutLectureThenNewLectureReplaceOldLecture () throws Exception {
        var id = UUID.randomUUID ();
        Lecture lecture = new Lecture();
        lecture.setName("Algebra");
        lecture.setRoom("12");
        lecture.setLectorName("Anna");
        lecture.setGroupIds ( List.of ("1", "2", "3") );
        lecture.setDateTime( LocalDateTime.of(2024,9,11,11,30, 0));



       doNothing ().when(lectureService).updateLecture ( lecture, id);

        mockMvc.perform ( put ( "/lectures/{id}" ,id  )
                        .contentType ( MediaType.APPLICATION_JSON )
                        .content ( """
                                
                                  {
                               
                                    "name": "Algebra",
                                    "groupIds": [
                                      "1",
                                      "2",
                                      "3"
                                    ],
                                    "lectorName": "Anna",
                                    "room": "12",
                                    "dateTime": "2024-09-11T11:30:00"
                                  }
                                """ ) )
                .andExpect (status().isOk () );

        verify ( lectureService ).updateLecture ( lecture, id );
    }

    @Test
    void ifPutLectureOnInvalidPathThenNewThrowLectureNotFoundException () throws Exception {
        var id = UUID.randomUUID ();
        Lecture lecture = new Lecture();
        lecture.setName("Algebra");
        lecture.setRoom("12");
        lecture.setLectorName("Anna");
        lecture.setGroupIds ( List.of ("1", "2", "3") );
        lecture.setDateTime( LocalDateTime.of(2024,9,11,11,30, 0));



        doThrow (new LectureNotFoundException ( "LECTURE WITH THIS ID "+ id +" NOT FOUND" ) )
                .when(lectureService).updateLecture ( lecture, id);

        mockMvc.perform ( put ( "/lectures/{id}" ,id  )
                        .contentType ( MediaType.APPLICATION_JSON )
                        .content ( """
                                
                                  {
                               
                                    "name": "Algebra",
                                    "groupIds": [
                                      "1",
                                      "2",
                                      "3"
                                    ],
                                    "lectorName": "Anna",
                                    "room": "12",
                                    "dateTime": "2024-09-11T11:30:00"
                                  }
                                """ ) )
                .andExpect (status().isNotFound () ).andExpect ( content ()
                        .json ( "{\"error\":null,\"code\":null," +
                                "\"message\":\"LECTURE WITH THIS ID "+ id +" NOT FOUND\"," +
                                "\"invalidValue\":null,\"field\":null}" ) );


    }

    @Test
    void IfDeleteLectureThanLectureRemove() throws Exception{
        var lectureId = UUID.randomUUID ();
        doNothing().when ( lectureService).deleteLecture ( lectureId );

        mockMvc.perform ( delete ( "/lectures/{id}", lectureId ) )
                .andExpect ( status ().isOk () );
    }

}