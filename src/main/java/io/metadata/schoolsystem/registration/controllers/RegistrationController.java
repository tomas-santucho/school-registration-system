package io.metadata.schoolsystem.registration.controllers;

import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.registration.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.registration.services.RegistrationService;
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

import static io.metadata.schoolsystem.utils.Endpoints.REGISTER;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/students")
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping(REGISTER)
    ResponseEntity<?> addStudent(@RequestBody Student student, @RequestBody Course course) throws MaxCoursesException, StudentNotFoundException {
        var entityModel = assembler.toModel(service.register(student.getId(), course.getId()));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}