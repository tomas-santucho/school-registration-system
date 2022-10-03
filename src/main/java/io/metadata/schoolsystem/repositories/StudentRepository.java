package io.metadata.schoolsystem.repositories;

import io.metadata.schoolsystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(
            value = "SELECT s FROM Student s JOIN s.courses c WHERE c.id = :#{#course_id}")
    Set<Student> findAllByCourseId(@Param("course_id") long id);

    @Query(
            value = "SELECT * from Student s inner join student_courses sc on s.id <> sc.student_id", nativeQuery = true)
    Set<Student> findStudentsWithoutCourses();
}
