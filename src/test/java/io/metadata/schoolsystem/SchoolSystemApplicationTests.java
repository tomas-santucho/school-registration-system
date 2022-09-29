package io.metadata.schoolsystem;

import io.metadata.schoolsystem.courses.controllers.CoursesController;
import io.metadata.schoolsystem.students.controllers.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SchoolSystemApplicationTests {

    @Autowired
    private StudentController studentController;
    @Autowired
    private CoursesController coursesController;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
        assertThat(coursesController).isNotNull();
    }

}
