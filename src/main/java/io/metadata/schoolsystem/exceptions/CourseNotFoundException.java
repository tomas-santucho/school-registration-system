package io.metadata.schoolsystem.exceptions;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(long id) {
        super(String.valueOf(id));
    }
}
