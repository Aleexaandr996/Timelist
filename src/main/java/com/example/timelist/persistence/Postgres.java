package com.example.timelist.persistence;

public class Postgres {
    CREATE TABLE group(
      id UUID,
      name char(4) not null,
            PRIMARY KEY (id)
    );

    CREATE TABLE lecture(
            id UUID,
            lectorName varchar(15) not null,
            room numeric(3) not null,
            dateTime timestemp with time zone not null,
            name varchar(25) not null,
    PRIMARY KEY (id)
    );

    CREATE TABLE student(
            studentId UUID,
            studentGroup UUID,
            age numeric(2) not null,
            name varchar(15) not null,
    PRIMARY KEY (id),
    Foreign Key (studentGroup)
    );

    CREATE TABLE groupAndLecture(
            groupId UUID,
            lectureId UUID
    )
}
