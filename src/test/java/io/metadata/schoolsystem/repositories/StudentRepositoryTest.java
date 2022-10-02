package io.metadata.schoolsystem.repositories;

import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.repositories.StudentRepository;
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
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;
    private final String name = "John";
    private final String surname = "Smith";
    private final Course javaCourse = new Course("Java");

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
        //given
        var courses = List.of(javaCourse);
        var student = Student.builder().
                name(name).
                surname(surname).
                courses((courses)).
                build();
        //when
        var savedStudent = repository.save(student);
        //then
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
        assertThat(savedStudent.getName()).isEqualTo(name);
        assertThat(savedStudent.getSurname()).isEqualTo(surname);
        assertThat(savedStudent.getCourses()).containsExactly(javaCourse);
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
        var student = providesStudent();
        var savedStudent = repository.save(student);
        //WHEN
        var newStudent = repository.findById(savedStudent.getId());
        //THEN
        assertThat(newStudent).isPresent();
        var finalStudent = newStudent.get();
        assertThat(finalStudent).isNotNull();
        assertThat(finalStudent.getId()).isGreaterThan(0);
        assertThat(finalStudent.getName()).isEqualTo(student.getName());
        assertThat(finalStudent.getSurname()).isEqualTo(student.getSurname());
        assertThat(finalStudent.getCourses()).containsExactly(student.getCourses().get(0));
    }

    private Student saveStudent() {
        return repository.save(providesStudent());
    }
}