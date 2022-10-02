package io.metadata.schoolsystem.services;

import io.metadata.schoolsystem.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.exceptions.MaxStudentsException;
import io.metadata.schoolsystem.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.models.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static io.metadata.schoolsystem.providers.CourseProviders.providesCourse;
import static io.metadata.schoolsystem.providers.StudentProviders.providesStudent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {


    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;


    @InjectMocks
    private RegistrationService registrationService;

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void register() throws StudentNotFoundException, CourseNotFoundException {
        //GIVEN
        var student = providesStudent();
        var id = 4L;
        var course = providesCourse();
        var courses = new ArrayList<Course>();
        for (int i = 0; i < RegistrationService.MAX_COURSES_PER_STUDENT; i++) {
            courses.add(course);
        }
        student.setCourses(courses);
        given(studentService.findByIdOrThrow(id)).willReturn(student);
        given(courseService.findByIdOrThrow(id)).willReturn(course);
        //WHEN
        var e = assertThrows(MaxCoursesException.class, () -> {
            registrationService.register(id, id);
        });
        //THEN
        assertThat(e.getMessage()).contains(String.valueOf(student.getId()));
    }

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void register1() throws StudentNotFoundException, CourseNotFoundException {
        //GIVEN
        var student = providesStudent();
        var id = 4L;
        var course = providesCourse();
        var students = new ArrayList<Student>();
        for (int i = 0; i < RegistrationService.MAX_STUDENTS_PER_COURSE; i++) {
            students.add(student);
        }
        course.setStudents(students);
        given(studentService.findByIdOrThrow(id)).willReturn(student);
        given(courseService.findByIdOrThrow(id)).willReturn(course);
        //WHEN
        var e = assertThrows(MaxStudentsException.class, () ->
                registrationService.register(id, id)
        );
        //THEN
        assertThat(e.getMessage()).contains(String.valueOf(course.getId()));
    }
}