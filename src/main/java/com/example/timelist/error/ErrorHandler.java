package com.example.timelist.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    private GroupDuplicateException e;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGroupNotFound(GroupNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .message (e.getMessage()).build();
        return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleLectureNotFound(LectureNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .message (e.getMessage())
                .build();
        return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body(response);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .message (e.getMessage())
                .build();
        return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGroGroupSizeStudentException(StudentSizeInGroupException e) {
        ErrorResponse build = ErrorResponse.builder().message (e.getMessage())
                .error("STUDENT SIZE IN GROUP").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGroupDuplicateException(GroupDuplicateException e) {
        ErrorResponse build = ErrorResponse.builder().message ("Group with this name already exist")
                .error("DUPLICATED GROUP").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleLectureDuplicateException(LectureDuplicateException e) {
        ErrorResponse build = ErrorResponse.builder()
                .message ("Lecture with this name already exist at this time and this room")
                .error("DUPLICATED LECTURE").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleLectureRoom(LectureRoomException e) {
        ErrorResponse build = ErrorResponse.builder().message ("LECTURE ROOM IS BOOKED ON THIS DAY AND TIME")
                .error("LECTURE ROOM EXCEPTION").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleStudentDuplicate(StudentDuplicateException e) {
        ErrorResponse build = ErrorResponse.builder().message ("Student with this information already exist")
                .error("STUDENT DUPLICATE").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleStudentInGroup(StudentDuplicateInGroupException e) {
        ErrorResponse build = ErrorResponse.builder().message (e.getMessage()).
                error("400").error("STUDENT DUPLICATE NAME IN GROUP").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidBindRequest (BindException e) {
        log.info("Invalid Bind Exception request");
        String field = Objects.requireNonNull(e.getFieldError()).getField();
        Object rejectedValue = e.getFieldError().getRejectedValue();
        assert rejectedValue != null;
        ErrorResponse build = ErrorResponse.builder().field(field).message ( "INVALID EXCEPTION" ).
                invalidValue(rejectedValue.toString()).code("400").build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInArgumentNotValidRequest (MethodArgumentNotValidException e) {
        log.info("Invalid Method Argument Not Valid Exception request");
        String field = Objects.requireNonNull(e.getFieldError()).getField();

        Object rejectedValue = e.getFieldError().getRejectedValue();
        assert rejectedValue != null;
        ErrorResponse build = ErrorResponse.builder().field(field).message ( "INVALID EXCEPTION" ).
                invalidValue(rejectedValue.toString()).code("400").build();
        return ResponseEntity.badRequest().body(build);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception e) {
        log.info("Unknown exception", e);
        ErrorResponse build = ErrorResponse.builder().message ("UNKNOWN EXCEPTION").build();
        return ResponseEntity.badRequest().body(build);
    }

}
