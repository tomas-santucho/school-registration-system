package io.metadata.schoolsystem.controllers;

import io.metadata.schoolsystem.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.exceptions.MaxStudentsException;
import io.metadata.schoolsystem.services.RegistrationService;
import io.metadata.schoolsystem.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.asemblers.StudentModelAssembler;
import io.metadata.schoolsystem.models.Student;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.metadata.schoolsystem.utils.Endpoints.*;

@RestController
@RequestMapping(REGISTRATION)
public class RegistrationController {

    private final RegistrationService service;
    private final StudentModelAssembler assembler;

    public RegistrationController(RegistrationService service, StudentModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping(REGISTER_WITH_ID)
    ResponseEntity<?> registerWithId(@RequestBody long studentId, @RequestBody long courseId) throws MaxCoursesException, StudentNotFoundException, CourseNotFoundException, MaxStudentsException {
        var entityModel = assembler.toModel(service.register(studentId, courseId));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}