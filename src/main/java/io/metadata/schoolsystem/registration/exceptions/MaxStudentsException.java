package io.metadata.schoolsystem.registration.exceptions;

import io.metadata.schoolsystem.models.Course;

public class MaxStudentsException extends Exception{
    public MaxStudentsException(final Course c) {
        super("Course with id: + "+c.getId()+" reached it student limit.");
    }
}
