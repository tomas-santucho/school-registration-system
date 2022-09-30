package io.metadata.schoolsystem.registration.controllers;

import io.metadata.schoolsystem.courses.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.registration.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.registration.exceptions.MaxStudentsException;
import io.metadata.schoolsystem.registration.services.RegistrationService;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.students.modelAsammbler.StudentModelAssembler;
import io.metadata.schoolsystem.students.models.Student;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.metadata.schoolsystem.utils.Endpoints.REGISTER;

@RestController
@RequestMapping("/students")
public class RegistrationController {

    private final RegistrationService service;
    private final StudentModelAssembler assembler;

    public RegistrationController(RegistrationService service, StudentModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping(REGISTER)
    ResponseEntity<?> register(@RequestBody Student student, @RequestBody Course course) throws MaxCoursesException, StudentNotFoundException, CourseNotFoundException, MaxStudentsException {
        var entityModel = assembler.toModel(service.register(student.getId(), course.getId()));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}