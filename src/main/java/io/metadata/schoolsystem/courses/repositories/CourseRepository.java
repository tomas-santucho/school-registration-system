package io.metadata.schoolsystem.courses.repositories;

import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.students.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
