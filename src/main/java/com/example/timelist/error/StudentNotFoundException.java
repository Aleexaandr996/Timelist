package com.example.timelist.error;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException (String message) {
        super ( message );
    }
}
