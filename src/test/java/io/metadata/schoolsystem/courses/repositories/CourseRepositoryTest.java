package io.metadata.schoolsystem.courses.repositories;

import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.repositories.CourseRepository;
import io.metadata.schoolsystem.models.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static io.metadata.schoolsystem.providers.StudentProviders.providesStudent;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;
    private final String name = "Java Course";
    private final Student Student = providesStudent();

    @AfterEach
    void afterAll() {
        repository.deleteAll();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void save() {
        var course = providesCourse();
        //when
        var savedCourse = repository.save(course);
        //then
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getId()).isGreaterThan(0);
        assertThat(savedCourse.getName()).isEqualTo(course.getName());
        assertThat(savedCourse.getStudents()).containsExactly(course.getStudents().get(0));
    }

    @Test
    @DisplayName("WHEN findAll is called THEN it returns a list with all the students")
    public void findAll() {
        //when
        var savedStudents = repository.findAll();
        //then
        assertThat(savedStudents).isNotNull();
        assertThat(savedStudents.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("WHEN student is deleted THEN is not present")
    public void deleteById() {
        //GIVEN
        var studentId = saveStudent().getId();
        //WHEN
        repository.deleteById(studentId);
        //THEN
        assertThat(repository.findById(studentId)).isNotPresent();
    }

    @Test
    @DisplayName("WHEN student is deleted THEN is present")
    public void findById() {
        //GIVEN
        var student = providesCourse();
        var savedStudent = repository.save(student);
        //WHEN
        var newStudent = repository.findById(savedStudent.getId());
        //THEN
        assertThat(newStudent).isPresent();
        var finalStudent = newStudent.get();
        assertThat(finalStudent).isNotNull();
        assertThat(finalStudent.getId()).isGreaterThan(0);
        assertThat(finalStudent.getName()).isEqualTo(student.getName());
        assertThat(finalStudent.getStudents()).containsExactly(student.getStudents().get(0));
    }

    private Course saveStudent() {
        return repository.save(providesCourse());
    }

    private Course providesCourse() {
        var courses = List.of(Student);
        return Course.builder().name(name).students(courses).build();
    }
}