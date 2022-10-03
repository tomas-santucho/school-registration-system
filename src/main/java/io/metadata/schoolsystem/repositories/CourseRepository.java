package io.metadata.schoolsystem.repositories;

import io.metadata.schoolsystem.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(
            value = "SELECT c FROM Course c JOIN c.students s WHERE s.id = :#{#student_id}")
    Set<Course> findAllByStudentId(@Param("student_id") long id);
    @Query(
            value = "SELECT * from Courses c inner join student_courses sc on c.id <> sc.course_id", nativeQuery = true)
    Set<Course> findEmptyCourses();
}
