package io.metadata.schoolsystem.courses.controllers;

import io.metadata.schoolsystem.courses.modelAsammbler.CourseModelAssembler;
import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.courses.repositories.CourseRepository;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.students.modelAsammbler.StudentModelAssembler;
import io.metadata.schoolsystem.students.models.Student;
import io.metadata.schoolsystem.students.repositories.StudentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private final CourseRepository service;
    private final CourseModelAssembler assembler;

    public CoursesController(CourseRepository aStudentService, CourseModelAssembler anAssembler) {
        this.service = aStudentService;
        this.assembler = anAssembler;
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<Course>> all() {
        List<EntityModel<Course>> students = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(students, linkTo(methodOn(CoursesController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Course> one(@PathVariable Long id) {
        //FIX THIS
        final Course student;
        try {
            student = service.findById(id)
                    .orElseThrow(() -> new StudentNotFoundException(id.toString()));
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        return assembler.toModel(student);
    }

    @PostMapping("/register")
    ResponseEntity<?> addStudent(@RequestBody Course newStudent) {
        var entityModel = assembler.toModel(service.save(newStudent));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @PutMapping("/update/{id}")
    ResponseEntity<?> updateStudent(@RequestBody Course newStudent, @PathVariable Long id) {
        var updatedStudent = service.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                   // student.setSurname(newStudent.getSurname());
                   // student.setBornDate(newStudent.getBornDate());
                    return service.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return service.save(newStudent);
                });
        var entityModel = assembler.toModel(updatedStudent);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}