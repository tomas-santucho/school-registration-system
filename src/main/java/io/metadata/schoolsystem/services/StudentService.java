package io.metadata.schoolsystem.services;

import io.metadata.schoolsystem.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.models.Student;
import io.metadata.schoolsystem.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student findByIdOrThrow(final long id) throws StudentNotFoundException {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Optional<Student> findById(final long id) {
        return repository.findById(id);
    }

    public Student save(final Student s) {
        return repository.save(s);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public void deleteById(final long id) {
        repository.deleteById(id);
    }

    public Set<Student> findAllByCourseId(final long id) {
        return repository.findAllByCourseId(id);
    }

    public Set<Student> findStudentsWithoutCourses() {
        return repository.findStudentsWithoutCourses();
    }
}
