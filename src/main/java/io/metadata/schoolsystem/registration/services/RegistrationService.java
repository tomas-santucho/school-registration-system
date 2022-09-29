package io.metadata.schoolsystem.registration.services;

import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.courses.repositories.CourseRepository;
import io.metadata.schoolsystem.registration.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.students.services.StudentService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService{

    private final static int MAX_STUDENTS_PER_COURSE = 50;
    private final static int MAX_COURSES_PER_STUDENT = 5;
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    public RegistrationService(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public void register(final long studentId, final long courseId) throws StudentNotFoundException, MaxCoursesException {
        var student = studentService.findByIdOrThrow(studentId);
        var course = courseRepository.findById(courseId).orElseGet(new Course());
        var studentCourses =  student.getCourses();
        if (studentCourses.size()>=MAX_COURSES_PER_STUDENT){
            throw new MaxCoursesException(student);
        }else if (course.getStudents().size()>=MAX_STUDENTS_PER_COURSE){
            throw new MaxCoursesException(student);
        }else{
            studentCourses.add(course);
            studentService.save(student);
        }
    }
}
