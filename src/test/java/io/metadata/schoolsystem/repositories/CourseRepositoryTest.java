package io.metadata.schoolsystem.repositories;

import io.metadata.schoolsystem.models.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static io.metadata.schoolsystem.providers.CourseProviders.providesCourse;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository repository;

    @AfterEach
    void afterAll() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("WHEN course is saved THEN it properties are correct")
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
    @DisplayName("WHEN findAll is called THEN it returns a list with all the course")
    public void findAll() {
        //when
        var savedStudents = repository.findAll();
        //then
        assertThat(savedStudents).isNotNull();
        assertThat(savedStudents.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("WHEN course is deleted THEN is not present")
    public void deleteById() {
        //GIVEN
        var studentId = saveCourse().getId();
        //WHEN
        repository.deleteById(studentId);
        //THEN
        assertThat(repository.findById(studentId)).isNotPresent();
    }

    @Test
    @DisplayName("WHEN findById is deleted THEN it returns the right student")
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

    private Course saveCourse() {
        return repository.save(providesCourse());
    }

}