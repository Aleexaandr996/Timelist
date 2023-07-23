package com.example.timelist.controller;

import com.example.timelist.beans.Student;
import com.example.timelist.error.StudentDuplicateException;
import com.example.timelist.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerMvcTest {
    @MockBean
    StudentService studentService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void createMockMvc() {
        assertThat( mockMvc ).isNotNull();
    }

    @Test
    void IfCreateStudentThenStudentIsCreated () throws Exception {
        var studentId = UUID.randomUUID ().toString ();

        when(studentService.addStudent ( any ( Student.class) )).thenReturn ( studentId );

        mockMvc.perform ( post ( "/students" )
                .contentType ( MediaType.APPLICATION_JSON ).content ( """
                                {
                                  "name" : "Aloe",
                                  "age" : 15
                                  }""" ) )
                .andExpect (status().isOk () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ().json ( "{\n" +
                        " \"studentId\" : \"" + studentId + "\"\n" +
                        "  \n" +
                        "}" ) );
    }

    @Test
    void IfCreateStudentWithInvalidNameThenStudentIsNotCreated  () throws Exception {
        var studentId = UUID.randomUUID ().toString ();

        when(studentService.addStudent ( any ( Student.class) )).thenReturn ( studentId );

        mockMvc.perform ( post ( "/students" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                                {
                                  "name" : "",
                                  "age" : 15
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
    void IfCreateDuplicateStudentThenThrowDuplicateStudentException () throws Exception {
        Student student = new Student ();
        student.setName ( "Fin" );
        student.setAge ( 16 );
        student.setStudentId (UUID.randomUUID ().toString ());

       when(studentService.addStudent ( student )).
               thenThrow ( new StudentDuplicateException ( "Student with this information already exist" ) );

        mockMvc.perform ( post ( "/students" )
                        .contentType ( MediaType.APPLICATION_JSON )
                        .content( "{\n" +
                                  "  \"name\" : \"Fin\",\n" +
                                  "  \"age\" : 16,\n" +
                                  "  \"studentId\" : \""+student.getStudentId ()+"\"\n" +
                                                                             "}") )
                .andExpect ( status ().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ().json ( "{\"error\":\"STUDENT DUPLICATE\",\"code\":null," +
                        "\"message\":\"Student with this information already exist\"," +
                        "\"invalidValue\":null,\"field\":null}" ) );
    }

    @Test
    void IfCreateStudentWithInvalidAgeThenStudentIsNotCreated () throws Exception {
        var studentId = UUID.randomUUID ().toString ();

        when(studentService.addStudent ( any ( Student.class) )).thenReturn ( studentId );

        mockMvc.perform ( post ( "/students" )
                        .contentType ( MediaType.APPLICATION_JSON ).content ( """
                                {
                                  "name" : "Fin",
                                  "age" : 13
                                  }""" ) )
                .andExpect (status().isBadRequest () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ()
                        .json ( """
                                {
                                                      "error": null,
                                                      "code": "400",
                                                      "message": "INVALID EXCEPTION",
                                                      "invalidValue": "13",
                                                      "field": "age"
                                                    }""" ) );
    }

    @Test
    void IfGetStudentsThenDoGetAllStudents () throws Exception {
        Student student = new Student ();
        student.setName ( "Fin" );
        student.setAge ( 16 );
        student.setStudentId (UUID.randomUUID ().toString ());

        List<Student> expectedStudents = new ArrayList<> ();
        expectedStudents.add ( student );

        when ( studentService.getStudents () ).thenReturn ( expectedStudents );

        mockMvc.perform ( get ( "/students" ) )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( content ().json ( "[\n" +
                        "    {\n" +
                        "        \"studentId\": \"" + student.getStudentId () + "\",\n" +
                        "        \"name\": \"Fin\",\n" +
                        "        \"age\": 16\n" +
                        "    }\n" +
                        "]" ) );
    }

    @Test
    void IfPutStudentThenNewStudentReplaceOldStudent () throws Exception {
        Student student = new Student ();
        student.setName ( "Fin" );
        student.setAge ( 16 );
        var id = UUID.randomUUID ().toString ();

        doNothing ().when ( studentService ).updateStudent ( student ,student.getStudentId ());

        mockMvc.perform ( put("/students/{id}" , id)
                .contentType ( MediaType.APPLICATION_JSON ).content ( """
                        {
                          "name": "Fin",
                          "age": 16
                        }""" ) ).andExpect ( status ().isOk () );
        verify ( studentService ).updateStudent ( student, id );
    }

    @Test
    void ifDeleteStudentThenThisStudentRemove () throws Exception {
        var id = UUID.randomUUID ();
        doNothing ().when ( studentService ).deleteStudent ( id );

        mockMvc.perform ( MockMvcRequestBuilders.delete ( "/students/{id}", id ) )
                .andExpect ( status ().isOk () );
    }
}