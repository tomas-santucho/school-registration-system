package io.metadata.schoolsystem.registration.services;

import io.metadata.schoolsystem.courses.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.courses.services.CourseService;
import io.metadata.schoolsystem.registration.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.registration.exceptions.MaxStudentsException;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.students.models.Student;
import io.metadata.schoolsystem.students.services.StudentService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final static int MAX_STUDENTS_PER_COURSE = 50;
    private final static int MAX_COURSES_PER_STUDENT = 5;
    private final CourseService courseRepository;
    private final StudentService studentService;

    public RegistrationService(CourseService courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public Student register(final long studentId, final long courseId) throws StudentNotFoundException, MaxCoursesException, CourseNotFoundException, MaxStudentsException {
        var student = studentService.findByIdOrThrow(studentId);
        var course = courseRepository.findByIdOrThrow(courseId);
        var studentCourses = student.getCourses();
        if (studentCourses.size() >= MAX_COURSES_PER_STUDENT) {
            throw new MaxCoursesException(student);
        } else if (course.getStudents().size() >= MAX_STUDENTS_PER_COURSE) {
            throw new MaxStudentsException(course);
        } else {
            studentCourses.add(course);
            return studentService.save(student);
        }
    }
}
