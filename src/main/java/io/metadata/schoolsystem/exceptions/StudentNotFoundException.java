package io.metadata.schoolsystem.exceptions;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException(long id) {
        super(String.valueOf(id));
    }
}
