package io.metadata.schoolsystem.registration.exceptions;

import io.metadata.schoolsystem.students.models.Student;

public class MaxCoursesException extends Exception{
    public MaxCoursesException(final Student s) {
        super("Student with id: + "+s.getId()+" has reached its course limit");
    }
}
