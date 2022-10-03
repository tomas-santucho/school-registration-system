package io.metadata.schoolsystem.providers;

import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.models.Student;

import java.util.List;

public class CourseProviders {
    public static Course providesCourse() {
      return new Course("Java Course");
    }

    public static Course providesNullCourse() {
        return null;
    }
}
