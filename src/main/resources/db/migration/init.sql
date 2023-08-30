    CREATE TABLE student_group(
            id UUID,
      group_name CHAR(4) NOT NULL,
            PRIMARY KEY (id)
    );

    CREATE TABLE lecture(
            id UUID,
            lectorName VARCHAR(15) NOT NULL,
            room NUMERIC(3) NOT NULL,
            dateTime  TIMESTAMP WITH TIME ZONE NOT NULL,
            lecture_name VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
    );

    CREATE TABLE student(
            id UUID,
            student_Group_id UUID,
            age NUMERIC(2) NOT NULL,
            student_name VARCHAR(15) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_student_student_group FOREIGN KEY (student_Group_id) references student_group(id)
    );

    CREATE TABLE group_lecture(
            student_group_Id UUID NOT NULL,
            lecture_Id UUID NOT NULL,
            CONSTRAINT pk_student_group_lecture PRIMARY KEY (student_group_Id, lecture_Id),
            CONSTRAINT fk_lecture_student_group_id FOREIGN KEY (student_group_Id) REFERENCES student_group(id),
            CONSTRAINT fk_student_group_lecture_id FOREIGN KEY (lecture_Id) REFERENCES lecture(id)
    );