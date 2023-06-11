package com.example.timelist.error;

public class StudentDuplicateException extends RuntimeException{
    public StudentDuplicateException(String message) {
        super(message);
    }
}
