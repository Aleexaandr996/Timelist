package com.example.timelist.error;

public class LectureNotFoundException extends RuntimeException {
    public LectureNotFoundException (String message) {
        super ( message );
    }
}
