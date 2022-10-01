package io.metadata.schoolsystem.students.controllers;

import io.metadata.schoolsystem.courses.controllers.CoursesController;
import io.metadata.schoolsystem.courses.repositories.CourseRepository;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.students.modelAsammbler.StudentModelAssembler;
import io.metadata.schoolsystem.students.models.Student;
import io.metadata.schoolsystem.students.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.metadata.schoolsystem.utils.Endpoints.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping(STUDENTS)
public class StudentController {

    private final StudentService service;

    @Autowired
    CourseRepository courseRepository;

    private final StudentModelAssembler assembler;

    public StudentController(StudentService aStudentService, StudentModelAssembler anAssembler) {
        this.service = aStudentService;
        this.assembler = anAssembler;
    }

    @GetMapping(ALL)
    public CollectionModel<EntityModel<Student>> all() {
        List<EntityModel<Student>> students = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(students, linkTo(methodOn(StudentController.class).all()).withSelfRel());
    }

    @GetMapping(ID)
    public EntityModel<Student> one(@PathVariable Long id) {
        try {
            return assembler.toModel(service.findByIdOrThrow(id));
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(FIND_BY_COURSE_ID)
    public CollectionModel<EntityModel<Student>> byCourseId(@PathVariable Long id) {
        var students = service.findAllByCourseId(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toSet());

        return CollectionModel.of(students, linkTo(methodOn(StudentController.class).all()).withSelfRel());
    }

    @GetMapping(STUDENTS_WITHOUT_COURSES)
    public CollectionModel<EntityModel<Student>> emptyCourses() {
        var students = service.findStudentsWithoutCourses().stream()
                .map(assembler::toModel)
                .collect(Collectors.toSet());

        return CollectionModel.of(students, linkTo(methodOn(CoursesController.class).all()).withSelfRel());
    }

    @PostMapping(REGISTER)
    ResponseEntity<?> addStudent(@RequestBody Student newStudent) {
        var entityModel = assembler.toModel(service.save(newStudent));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping(UPDATE)
    ResponseEntity<?> updateStudent(@RequestBody Student newStudent, @PathVariable Long id) {
        var updatedStudent = service.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setSurname(newStudent.getSurname());
                    student.setBornDate(newStudent.getBornDate());
                    return service.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return service.save(newStudent);
                });
        var entityModel = assembler.toModel(updatedStudent);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping(DELETE)
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}