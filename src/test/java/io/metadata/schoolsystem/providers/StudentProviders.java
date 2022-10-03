package io.metadata.schoolsystem.providers;

import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.models.Student;

import java.util.List;

public class StudentProviders {

    public static Student providesStudent(String name, String surname, Course course) {
        var courses = List.of(course);
        return Student.builder().
                name(name).
                surname(surname).
                courses((courses)).
                build();
    }

    public static Student providesStudent() {
        var courses = List.of(new Course("Java Course"));
        return Student.builder().
                name("John").
                surname("Smith").
                courses((courses)).
                build();
    }

    public static Student providesNullStudent() {
        return null;
    }


}
