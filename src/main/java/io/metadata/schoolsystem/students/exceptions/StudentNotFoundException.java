package io.metadata.schoolsystem.students.exceptions;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException(long id) {
        super(String.valueOf(id));
    }
}
