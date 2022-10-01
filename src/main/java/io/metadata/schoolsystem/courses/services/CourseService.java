package io.metadata.schoolsystem.courses.services;

import io.metadata.schoolsystem.courses.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.courses.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course findByIdOrThrow(final long id) throws CourseNotFoundException {
        return repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Optional<Course> findById(final long id) {
        return repository.findById(id);
    }

    public Course save(final Course s) {
        return repository.save(s);
    }

    public Set<Course> findAll() {
        return new HashSet<>(repository.findAll());
    }

    public void deleteById(final long id) {
        repository.deleteById(id);
    }

    public Set<Course> findEmptyCourses() {
        return repository.findEmptyCourses();
    }

    public Set<Course> findCourseByStudentId(final long id) {
        return repository.findAllByStudentId(id);
    }
}
