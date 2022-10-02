package io.metadata.schoolsystem.providers;

import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.models.Student;

import java.util.List;

public class CourseProviders {
    public static Student providesStudent(String name, String surname, Course course) {
        var courses = List.of(course);
        return Student.builder().
                name(name).
                surname(surname).
                courses((courses)).
                build();
    }

    public static Course providesCourse() {
      return new Course("Java Course");
    }

    public static Course providesNullCourse() {
        return null;
    }
}
