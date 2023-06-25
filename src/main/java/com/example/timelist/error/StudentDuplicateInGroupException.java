package com.example.timelist.error;

public class StudentDuplicateInGroupException extends RuntimeException{
    public StudentDuplicateInGroupException(String message) {
        super(message);
    }
}
