package com.example.timelist.error;

public class LectureDuplicateException extends RuntimeException {
    public LectureDuplicateException (String message) {
        super ( message );
    }
}
