package com.example.timelist.error;

public class GroupDuplicateException extends RuntimeException{

    public GroupDuplicateException (String message) {
        super ( message );
    }
}
