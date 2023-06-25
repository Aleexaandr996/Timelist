package com.example.timelist.controller;

import com.example.timelist.beans.Lecture;
import com.example.timelist.service.LectureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
        var lectureId = UUID.randomUUID ().toString ();
        when(lectureService.addLecture ( any ( Lecture.class) )).thenReturn ( lectureId );

        mockMvc.perform ( MockMvcRequestBuilders.post ( "/lectures" )
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
    void IfGetLecturesThanDoGetAllLectures () throws Exception {
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime( LocalDateTime.of(2024,9,10,12,30));
        lecture.setId ( UUID.randomUUID ().toString () );

        List<Lecture> expectedLectures = new ArrayList<> ();
        expectedLectures.add ( lecture );

        when(lectureService.getLectures()).thenReturn(expectedLectures);

        mockMvc.perform ( MockMvcRequestBuilders.get ( "/lectures" )
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
        Lecture lecture = new Lecture();
        lecture.setName("History");
        lecture.setRoom("25");
        lecture.setLectorName("Inna");
        lecture.setDateTime( LocalDateTime.of(2024,9,10,12,30));
        lecture.setId ( UUID.randomUUID ().toString () );


       doNothing ().when(lectureService).updateLecture ( lecture, lecture.getId ());

        mockMvc.perform ( MockMvcRequestBuilders.put ( "/lectures/{id}" ,lecture.getId ()  )
                        .contentType ( MediaType.APPLICATION_JSON )
                        .content ( """
                                [
                                  {
                               
                                    "name": "Algebra",
                                    "groupIds": [
                                      "1",
                                      "2",
                                      "3"
                                    ],
                                    "lectorName": "Anna",
                                    "room": "12",
                                    "dateTime": "2024-09-10T11:00:00"
                                  }
                                ]""" ) )
                .andExpect (status().isOk () );
    }

    @Test
    void IfDeleteLectureThanLectureRemove() throws Exception{
        var lectureId = UUID.randomUUID ();
        doNothing().when ( lectureService).deleteLecture ( lectureId );

        mockMvc.perform ( MockMvcRequestBuilders.delete ( "/lectures/{id}", lectureId ) )
                .andExpect ( status ().isOk () );
    }

}