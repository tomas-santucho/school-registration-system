package io.metadata.schoolsystem.students.services;

import io.metadata.schoolsystem.registration.repositories.StudentRepository;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.students.models.Student;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student findByIdOrThrow(final long id) throws StudentNotFoundException {
        return repository.findById(id).orElseThrow(()-> new StudentNotFoundException(id));
    }
    public Optional<Student> findById(final long id) {
        return repository.findById(id);
    }
    public Student save(final Student s) {
        return repository.save(s);
    }

    public Set<Student> findAll() {
        return new HashSet<>(repository.findAll());
    }

    public void deleteById(final long id){
        repository.deleteById(id);
    }

}
